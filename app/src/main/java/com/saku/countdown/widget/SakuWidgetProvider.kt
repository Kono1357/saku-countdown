package com.saku.countdown.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.saku.countdown.MainActivity
import com.saku.countdown.R
import org.json.JSONObject

/**
 * ホーム画面用 4×1 ウィジェット。
 * 「○日目　{最新メッセージ先頭20文字}…」を表示し、タップでメイン画面を開く。
 * データは SharedPreferences（saku_data）から直接読み取る。
 */
class SakuWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (id in appWidgetIds) {
            updateWidget(context, appWidgetManager, id)
        }
    }

    companion object {
        private const val PREFS_NAME = "saku_prefs"
        private const val KEY_DATA = "saku_data"

        fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, widgetId: Int) {
            val (day, message) = readData(context)
            val preview = if (message.isBlank()) {
                "朔的倒计时"
            } else {
                message.replace("○", day.toString()).take(20) + "…"
            }

            val views = RemoteViews(context.packageName, R.layout.saku_widget)
            views.setTextViewText(R.id.widget_text, "第${day}天　$preview")

            // タップでメイン画面を開く
            val intent = Intent(context, MainActivity::class.java)
            val pending = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, pending)

            appWidgetManager.updateAppWidget(widgetId, views)
        }

        private fun readData(context: Context): Pair<Int, String> {
            return try {
                val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                val json = prefs.getString(KEY_DATA, null) ?: return 0 to ""
                val obj = JSONObject(json)
                val day = obj.optInt("todayDayCount", 0)
                val message = obj.optString("lastMessage", "")
                day to message
            } catch (e: Exception) {
                0 to ""
            }
        }
    }
}
