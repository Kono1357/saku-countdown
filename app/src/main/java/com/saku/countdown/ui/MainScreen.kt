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
import com.saku.countdown.MainViewModel
import com.saku.countdown.ui.theme.MutedBlueGray
import com.saku.countdown.ui.theme.NightBlue
import com.saku.countdown.ui.theme.PaleGold
import com.saku.countdown.ui.theme.White
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * 画面1：主界面。
 * 显示已过天数、倒计时、朔的留言、目标信息。
 */
@Composable
fun MainScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToHistory: () -> Unit,
    viewModel: MainViewModel
) {
    val data = viewModel.data

    // 启动淡入
    var launched by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { launched = true }

    // 留言中的「○」替换为已过天数
    val messageText = if (data.lastMessage.isBlank()) {
        "点右上角齿轮设置目标，就能收到朔的留言"
    } else {
        data.lastMessage.replace("○", data.todayDayCount.toString())
    }

    // 距离目标还有几天（倒计时）——每次重组都重新算，避免跨天后数值不更新
    val remainingDays = data.targetDate.takeIf { it.isNotBlank() }?.let {
        runCatching {
            ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(it)).toInt()
        }.getOrNull()
    }
    val countdownText = when {
        remainingDays == null -> null
        remainingDays > 0 -> "距离目标还有 $remainingDays 天"
        remainingDays == 0 -> "目标就是今天"
        else -> "目标已过去 ${-remainingDays} 天"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NightBlue)
    ) {
        // 右上角设置按钮（齿轮图标）
        IconButton(
            onClick = onNavigateToSettings,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "设置",
                tint = MutedBlueGray
            )
        }

        // 右下角历史按钮
        TextButton(
            onClick = onNavigateToHistory,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
        ) {
            Text("历史", fontSize = 14.sp, color = MutedBlueGray)
        }

        AnimatedVisibility(
            visible = launched,
            enter = fadeIn(tween(500))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // 上部：已过天数
                Text(
                    text = "第${data.todayDayCount}天",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = PaleGold,
                    textAlign = TextAlign.Center
                )

                // 倒计时
                countdownText?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        color = PaleGold,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                // 中部：朔的留言（日期更新时淡入）
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

                Spacer(modifier = Modifier.height(48.dp))

                // 下部：目标名与目标日
                Text(
                    text = "目标: ${data.goalName.ifBlank { "未设置" }}　期限: ${data.targetDate.ifBlank { "未设置" }}",
                    fontSize = 12.sp,
                    color = MutedBlueGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
