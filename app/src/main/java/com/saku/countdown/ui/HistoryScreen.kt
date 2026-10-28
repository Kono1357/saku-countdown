package com.saku.countdown.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saku.countdown.MainViewModel
import com.saku.countdown.ui.theme.MutedBlueGray
import com.saku.countdown.ui.theme.NightBlue
import com.saku.countdown.ui.theme.PaleGold
import com.saku.countdown.ui.theme.White

/**
 * 画面3：历史界面。
 * 按日期倒序显示朔的留言历史。
 */
@Composable
fun HistoryScreen(
    onNavigateBack: () -> Unit,
    viewModel: MainViewModel
) {
    val history = viewModel.data.messageHistory

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NightBlue)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 头部
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "返回",
                        tint = White
                    )
                }
                Text("朔的言语，日积月累", fontSize = 16.sp, color = PaleGold)
            }

            if (history.isEmpty()) {
                // 无数据
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "还没有积攒下言语",
                        fontSize = 14.sp,
                        color = MutedBlueGray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(history.reversed()) { entry ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "第${entry.day}天　${entry.date}",
                                fontSize = 12.sp,
                                color = PaleGold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = entry.message.replace("○", entry.day.toString()),
                                fontSize = 16.sp,
                                color = White
                            )
                        }
                    }
                }
            }
        }
    }
}
