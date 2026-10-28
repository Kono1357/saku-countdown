package com.saku.countdown.data

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

/**
 * アプリの永続化データ。
 * SharedPreferences のキー "saku_data" に JSON 文字列として保存する。
 */
data class SakuData(
    val goalName: String = "",
    val targetDate: String = "",     // "yyyy-MM-dd"
    val startDate: String = "",      // "yyyy-MM-dd"（最初の目標設定日）
    val todayDayCount: Int = 0,      // 本日の経過日数
    val lastMessage: String = "",    // 前回表示したメッセージ本文
    val lastMessageIndex: Int = -1,  // 前回表示したプリセットのインデックス（重複回避用）
    val lastUpdateDate: String = "", // 最後にメッセージを更新した日付
    val messageTone: String = "standard",
    val customMessages: List<String> = emptyList(),   // カスタムメッセージ（最大20件）
    val messageHistory: List<HistoryEntry> = emptyList(), // 履歴（日付順に追記）
    val wishMemo: String = ""        // 願い事ノート（一行メモ）
)

/** 履歴1件分。 */
data class HistoryEntry(
    val day: Int,      // 経過日数
    val date: String,  // "yyyy-MM-dd"
    val message: String,
    val source: String, // "preset" / "custom" / "wish"
    val memo: String = "" // 履歴メモ（コメント）
)

/** SharedPreferences を使った読み書きロジック。 */
class SakuDataRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("saku_prefs", Context.MODE_PRIVATE)

    /** JSON 文字列を読み込んで SakuData に変換する。 */
    fun load(): SakuData {
        val jsonStr = prefs.getString(KEY_DATA, null) ?: return SakuData()
        return try {
            val obj = JSONObject(jsonStr)
            SakuData(
                goalName = obj.optString("goalName", ""),
                targetDate = obj.optString("targetDate", ""),
                startDate = obj.optString("startDate", ""),
                todayDayCount = obj.optInt("todayDayCount", 0),
                lastMessage = obj.optString("lastMessage", ""),
                lastMessageIndex = obj.optInt("lastMessageIndex", -1),
                lastUpdateDate = obj.optString("lastUpdateDate", ""),
                messageTone = obj.optString("messageTone", "standard"),
                customMessages = obj.optJSONArray("customMessages")?.let { arr ->
                    (0 until arr.length()).map { arr.optString(it, "") }.filter { it.isNotBlank() }
                } ?: emptyList(),
                messageHistory = obj.optJSONArray("messageHistory")?.let { arr ->
                    (0 until arr.length()).mapNotNull { i ->
                        val e = arr.optJSONObject(i) ?: return@mapNotNull null
                        HistoryEntry(
                            day = e.optInt("day", 0),
                            date = e.optString("date", ""),
                            message = e.optString("message", ""),
                            source = e.optString("source", "preset"),
                            memo = e.optString("memo", "")
                        )
                    }
                } ?: emptyList(),
                wishMemo = obj.optString("wishMemo", "")
            )
        } catch (e: Exception) {
            // JSON が壊れている場合は初期値を返す
            SakuData()
        }
    }

    /** SakuData を JSON 文字列に変換して保存する。 */
    fun save(data: SakuData) {
        val obj = JSONObject().apply {
            put("goalName", data.goalName)
            put("targetDate", data.targetDate)
            put("startDate", data.startDate)
            put("todayDayCount", data.todayDayCount)
            put("lastMessage", data.lastMessage)
            put("lastMessageIndex", data.lastMessageIndex)
            put("lastUpdateDate", data.lastUpdateDate)
            put("messageTone", data.messageTone)
            put("customMessages", JSONArray().apply {
                data.customMessages.forEach { put(it) }
            })
            put("messageHistory", JSONArray().apply {
                data.messageHistory.forEach { h ->
                    put(JSONObject().apply {
                        put("day", h.day)
                        put("date", h.date)
                        put("message", h.message)
                        put("source", h.source)
                        put("memo", h.memo)
                    })
                }
            })
            put("wishMemo", data.wishMemo)
        }
        prefs.edit().putString(KEY_DATA, obj.toString()).apply()
    }

    companion object {
        private const val KEY_DATA = "saku_data"
    }
}
