package com.saku.countdown

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.saku.countdown.data.HistoryEntry
import com.saku.countdown.data.SakuData
import com.saku.countdown.data.SakuDataRepository
import com.saku.countdown.data.presetMessages
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/** プリセット＋カスタムを統合した抽選候補。 */
private data class MessageCandidate(
    val id: Int,
    val text: String,
    val tone: String,
    val source: String // "preset" / "custom"
)

/**
 * 全画面の状態を管理する ViewModel。
 * SharedPreferences への読み書きと、日数・メッセージ・履歴の自動更新を担う。
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SakuDataRepository(application)

    // UI に公開する状態（Compose が監視する）
    var data by mutableStateOf(SakuData())
        private set

    init {
        refresh()
    }

    /** 起動・復帰時：保存データを読み込み、日数とメッセージを更新して永続化する。 */
    fun refresh() {
        var d = repository.load()
        val today = todayString()
        if (d.targetDate.isNotBlank()) {
            d = d.copy(todayDayCount = calcDayCount(d.startDate, today))
            d = updateMessage(d, today)
            // 日付をまたいだ更新（新メッセージ・日数・履歴）を確実に保存する
            repository.save(d)
        }
        data = d
    }

    /** 設定画面の保存：目標名・目標日・雰囲気・カスタムメッセージを反映する。 */
    fun saveSettings(
        goalName: String,
        targetDate: String,
        tone: String,
        customMessages: List<String>
    ) {
        val today = todayString()
        var d = data.copy(
            goalName = goalName.trim(),
            targetDate = targetDate,
            messageTone = tone,
            customMessages = customMessages.filter { it.isNotBlank() }
        )

        if (targetDate.isNotBlank()) {
            // 最初の目標設定日を自動記録（既に設定済みなら維持する）
            if (d.startDate.isBlank()) {
                d = d.copy(startDate = today)
            }
            d = d.copy(todayDayCount = calcDayCount(d.startDate, today))
        } else {
            // 目標日が空なら開始日と経過日数をリセット
            d = d.copy(startDate = "", todayDayCount = 0)
        }

        d = updateMessage(d, today)
        data = d
        repository.save(data)
    }

    /** 開始日から今日までの経過日数（今日を「1日目」とする）。 */
    private fun calcDayCount(startDate: String, today: String): Int {
        if (startDate.isBlank()) return 0
        return runCatching {
            val days = ChronoUnit.DAYS.between(
                LocalDate.parse(startDate),
                LocalDate.parse(today)
            ).toInt()
            maxOf(0, days) + 1
        }.getOrDefault(0)
    }

    /** 毎日1回だけ新しいメッセージを選び、履歴に追記して返す。 */
    private fun updateMessage(d: SakuData, today: String): SakuData {
        if (d.targetDate.isBlank()) {
            // 目標が空：メッセージを消し、更新日もリセット（同日に再設定したとき即メッセージを出せるように）
            return d.copy(lastMessage = "", lastMessageIndex = -1, lastUpdateDate = "")
        }
        // 今日すでに更新済み、かつ実際にメッセージがある場合だけスキップ（1日1回のルール）
        if (d.lastUpdateDate == today && d.lastMessage.isNotBlank()) return d

        val candidate = chooseMessage(d.messageTone, d.lastMessageIndex, d.customMessages)
        val entry = HistoryEntry(
            day = d.todayDayCount,
            date = today,
            message = candidate.text,
            source = candidate.source
        )
        return d.copy(
            lastMessage = candidate.text,
            lastMessageIndex = candidate.id,
            lastUpdateDate = today,
            messageHistory = d.messageHistory + entry
        )
    }

    /** トーンに合うメッセージを優先し、前回と連続で重複しないものを選ぶ。 */
    private fun chooseMessage(
        tone: String,
        previousIndex: Int,
        customMessages: List<String>
    ): MessageCandidate {
        // プリセット60個 + カスタムメッセージ全件
        val all = buildCandidates(customMessages)
        // 連続重複を避ける（候補が空になった場合は全体から選ぶ）
        val base = all.filter { it.id != previousIndex }.ifEmpty { all }

        // standard はトーンを絞らず全体から、それ以外は同名トーンを優先する
        val preferred = if (tone == "standard") emptyList() else base.filter { it.tone == tone }
        val candidates = if (preferred.isNotEmpty()) preferred else base
        return candidates.random()
    }

    /** プリセット（id=1〜60）とカスタム（id=1000〜、tone="custom"）を統合する。 */
    private fun buildCandidates(customMessages: List<String>): List<MessageCandidate> {
        val presets = presetMessages.map {
            MessageCandidate(it.id, it.text, it.tone, "preset")
        }
        val customs = customMessages.mapIndexed { i, text ->
            MessageCandidate(1000 + i, text, "custom", "custom")
        }
        return presets + customs
    }

    companion object {
        private fun todayString(): String =
            LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}
