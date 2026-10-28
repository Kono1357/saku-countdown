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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
 * 按日期倒序显示朔的留言历史，并可为每条留言添加备注。
 */
@Composable
fun HistoryScreen(
    onNavigateBack: () -> Unit,
    viewModel: MainViewModel
) {
    val history = viewModel.data.messageHistory

    // 正在编辑备注的那条（原始索引）与输入内容
    var editingIndex by remember { mutableStateOf<Int?>(null) }
    var editingMemo by remember { mutableStateOf("") }

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
                    itemsIndexed(history.reversed()) { revIndex, entry ->
                        val originalIndex = history.size - 1 - revIndex
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "第${entry.day}天　${entry.date}",
                                    fontSize = 12.sp,
                                    color = PaleGold,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        editingIndex = originalIndex
                                        editingMemo = entry.memo
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = "备注",
                                        tint = MutedBlueGray
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = entry.message.replace("○", entry.day.toString()),
                                fontSize = 16.sp,
                                color = White
                            )
                            if (entry.memo.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "备注：${entry.memo}",
                                    fontSize = 12.sp,
                                    color = MutedBlueGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // 备注编辑对话框
    editingIndex?.let { idx ->
        AlertDialog(
            onDismissRequest = { editingIndex = null },
            title = { Text("备注", color = White) },
            text = {
                OutlinedTextField(
                    value = editingMemo,
                    onValueChange = { editingMemo = it },
                    placeholder = { Text("写点什么…") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.updateHistoryMemo(idx, editingMemo)
                    editingIndex = null
                }) {
                    Text("保存", color = PaleGold)
                }
            },
            dismissButton = {
                TextButton(onClick = { editingIndex = null }) {
                    Text("取消", color = MutedBlueGray)
                }
            }
        )
    }
}
