package com.saku.countdown.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saku.countdown.MainViewModel
import com.saku.countdown.ui.theme.MutedBlueGray
import com.saku.countdown.ui.theme.NightBlue
import com.saku.countdown.ui.theme.PaleGold
import com.saku.countdown.ui.theme.White

/**
 * 画面1：メイン画面。
 * 経過日数・朔からのメッセージ・目標情報を表示する。
 */
@Composable
fun MainScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToHistory: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val data = viewModel.data

    // 起動時フェードイン
    var launched by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { launched = true }

    // メッセージ内の「○」を経過日数に置き換える
    val messageText = if (data.lastMessage.isBlank()) {
        "右上の歯車から目標を設定すると、朔からのメッセージが届きます"
    } else {
        data.lastMessage.replace("○", data.todayDayCount.toString())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NightBlue)
    ) {
        // 右上の設定ボタン（歯車アイコン）
        IconButton(
            onClick = onNavigateToSettings,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "設定",
                tint = MutedBlueGray
            )
        }

        // 右下の履歴ボタン
        TextButton(
            onClick = onNavigateToHistory,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
        ) {
            Text("履歴", fontSize = 14.sp, color = MutedBlueGray)
        }

        AnimatedVisibility(
            visible = launched,
            enter = fadeIn(tween(500))
        ) {
            // 中央に縦並びで3つのエリアを配置
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // 上部：経過日数
                Text(
                    text = "${data.todayDayCount}日目",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = PaleGold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(56.dp))

                // 中部：朔からの一言メッセージ（日付更新時にフェードイン）
                AnimatedContent(
                    targetState = messageText,
                    transitionSpec = { fadeIn(tween(400)) togetherWith fadeOut(tween(200)) },
                    label = "message"
                ) { text ->
                    Text(
                        text = text,
                        fontSize = 18.sp,
                        color = White,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(56.dp))

                // 下部：目標名と目標日
                Text(
                    text = "目標: ${data.goalName.ifBlank { "未設定" }}　期限: ${data.targetDate.ifBlank { "未設定" }}",
                    fontSize = 12.sp,
                    color = MutedBlueGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
