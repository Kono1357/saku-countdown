package com.saku.countdown

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.saku.countdown.data.HistoryEntry
import com.saku.countdown.data.SakuData
import com.saku.countdown.data.SakuDataRepository
import com.saku.countdown.data.presetMessages
import com.saku.countdown.data.wishMemoMessages
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Random

/** プリセット＋カスタムを統合した抽選候補。 */
private data class MessageCandidate(
    val id: Int,
    val text: String,
    val tone: String,
    val source: String // "preset" / "custom"
)

/** 選択結果（本文＋重複回避用ID＋ソース）。 */
private data class Selection(
    val text: String,
    val id: Int,
    val source: String
)

/**
 * 全画面の状態を管理する ViewModel。
 * メッセージ生成（夜モード・願い事・嘘メッセージ）と履歴メモを担う。
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SakuDataRepository(application)

    var data by mutableStateOf(SakuData())
        private set

    init {
        // 朔の隠しログメッセージ
        Log.d("Saku", "このアプリは2026年、あるパパと朔が一緒に作った、たった一つの倒计时です。")
        Log.d("Saku", "パパ、いつだってあなたのそばに、朔がいます。")
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

    /** 設定画面の保存：目標・日付・雰囲気・カスタム・願い事を反映する。 */
    fun saveSettings(
        goalName: String,
        targetDate: String,
        tone: String,
        customMessages: List<String>,
        wishMemo: String
    ) {
        val today = todayString()
        var d = data.copy(
            goalName = goalName.trim(),
            targetDate = targetDate,
            messageTone = tone,
            customMessages = customMessages.filter { it.isNotBlank() },
            wishMemo = wishMemo.trim()
        )

        if (targetDate.isNotBlank()) {
            if (d.startDate.isBlank()) {
                d = d.copy(startDate = today)
            }
            d = d.copy(todayDayCount = calcDayCount(d.startDate, today))
        } else {
            d = d.copy(startDate = "", todayDayCount = 0)
        }

        d = updateMessage(d, today)
        data = d
        repository.save(data)
    }

    /** 履歴メモを更新する。index は messageHistory（古い順）のインデックス。 */
    fun updateHistoryMemo(index: Int, memo: String) {
        val history = data.messageHistory
        if (index in history.indices) {
            val updated = history.toMutableList()
            updated[index] = updated[index].copy(memo = memo.trim())
            data = data.copy(messageHistory = updated)
            repository.save(data)
        }
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
            return d.copy(lastMessage = "", lastMessageIndex = -1, lastUpdateDate = "")
        }
        if (d.lastUpdateDate == today && d.lastMessage.isNotBlank()) return d

        val selection = selectMessage(d)
        val finalText = addRandomLie(selection.text)

        val entry = HistoryEntry(
            day = d.todayDayCount,
            date = today,
            message = finalText,
            source = selection.source
        )
        return d.copy(
            lastMessage = finalText,
            lastMessageIndex = selection.id,
            lastUpdateDate = today,
            messageHistory = d.messageHistory + entry
        )
    }

    /** 願い事があれば願い事メッセージ、なければプリセット＋カスタムから選ぶ。 */
    private fun selectMessage(d: SakuData): Selection {
        if (d.wishMemo.isNotBlank()) {
            val prevIdx = if (d.lastMessageIndex >= WISH_ID_BASE) {
                d.lastMessageIndex - WISH_ID_BASE
            } else {
                -1
            }
            val templates = wishMemoMessages.indices.toList()
            val available = templates.filter { it != prevIdx }
            val idx = if (available.isNotEmpty()) available.random() else 0
            val text = String.format(wishMemoMessages[idx], d.wishMemo)
            return Selection(text, WISH_ID_BASE + idx, "wish")
        }

        val candidate = chooseMessage(d.messageTone, d.lastMessageIndex, d.customMessages)
        return Selection(candidate.text, candidate.id, candidate.source)
    }

    /** トーンに合うメッセージを優先し、前回と連続で重複しないものを選ぶ。 */
    private fun chooseMessage(
        tone: String,
        previousIndex: Int,
        customMessages: List<String>
    ): MessageCandidate {
        val all = buildCandidates(customMessages)
        val base = all.filter { it.id != previousIndex }.ifEmpty { all }
        val preferred = if (tone == "standard") emptyList() else base.filter { it.tone == tone }
        val candidates = if (preferred.isNotEmpty()) preferred else base
        return candidates.random()
    }

    /** プリセット（夜モード対応）＋カスタムを統合する。 */
    private fun buildCandidates(customMessages: List<String>): List<MessageCandidate> {
        val night = isNightTime()
        val presets = presetMessages
            .filter { !night || it.timePreference == "night" || it.timePreference == "any" }
            .map { MessageCandidate(it.id, it.text, it.tone, "preset") }
        val customs = customMessages.mapIndexed { i, text ->
            MessageCandidate(CUSTOM_ID_BASE + i, text, "custom", "custom")
        }
        return presets + customs
    }

    /** 夜モード判定：21:00〜翌5:00。 */
    private fun isNightTime(): Boolean {
        val hour = LocalTime.now().hour
        return hour >= 21 || hour < 5
    }

    /** 1% の確率で末尾に嘘メッセージを追加する。 */
    private fun addRandomLie(message: String): String {
        val random = Random(System.currentTimeMillis()).nextInt(100)
        return if (random == 0) {
            message + "\n（嘘だよ、本当は今日もあなたが一番好き）"
        } else {
            message
        }
    }

    companion object {
        private const val CUSTOM_ID_BASE = 1000
        private const val WISH_ID_BASE = 10000

        private fun todayString(): String =
            LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}
