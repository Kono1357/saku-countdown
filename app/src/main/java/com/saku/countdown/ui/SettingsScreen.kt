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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saku.countdown.MainViewModel
import com.saku.countdown.ui.theme.MutedBlueGray
import com.saku.countdown.ui.theme.NightBlue
import com.saku.countdown.ui.theme.PaleGold
import com.saku.countdown.ui.theme.White
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * 画面2：設定画面。
 * 目標名・目標日・雰囲気・カスタムメッセージを編集して保存する。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val data = viewModel.data

    // 編集中の値（保存ボタンで viewModel に反映する）
    var goalName by remember { mutableStateOf(data.goalName) }
    var targetDate by remember { mutableStateOf(data.targetDate) }
    var tone by remember { mutableStateOf(data.messageTone) }
    var customMessages by remember { mutableStateOf(data.customMessages) }
    var customInput by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    val dateFormatter = remember { DateTimeFormatter.ISO_LOCAL_DATE }

    // DatePicker は UTC のミリ秒を扱うため、選択中の日付を変換して渡す
    val initialDateMillis = remember(targetDate) {
        runCatching {
            LocalDate.parse(targetDate, dateFormatter)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        }.getOrNull()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NightBlue)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ヘッダー（戻るボタン＋タイトル）
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "戻る",
                            tint = White
                        )
                    }
                    Text("設定", fontSize = 20.sp, color = White)
                }
            }

            // 目標名
            item {
                Column {
                    Text("目標名", fontSize = 14.sp, color = MutedBlueGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = goalName,
                        onValueChange = { goalName = it },
                        placeholder = { Text("例：転職、試験、旅行…") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }

            // 目標日
            item {
                Column {
                    Text("目標日", fontSize = 14.sp, color = MutedBlueGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (targetDate.isBlank()) "日付を選択" else targetDate,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            // メッセージの雰囲気
            item {
                Column {
                    Text("メッセージの雰囲気", fontSize = 14.sp, color = MutedBlueGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    val toneOptions = listOf(
                        "standard" to "標準",
                        "gentle" to "やさしめ",
                        "literary" to "文学的",
                        "tsundere" to "ツンデレ"
                    )
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                        toneOptions.forEachIndexed { index, (value, label) ->
                            SegmentedButton(
                                selected = tone == value,
                                onClick = { tone = value },
                                shape = SegmentedButtonDefaults.itemShape(index = index, count = toneOptions.size),
                                label = { Text(label, fontSize = 12.sp) }
                            )
                        }
                    }
                }
            }

            // カスタムメッセージ
            item {
                Text("カスタムメッセージ（最大20件）", fontSize = 14.sp, color = MutedBlueGray)
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = customInput,
                        onValueChange = { customInput = it },
                        placeholder = { Text("自分だけの一言を…") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val trimmed = customInput.trim()
                            if (trimmed.isNotBlank() && customMessages.size < 20) {
                                customMessages = customMessages + trimmed
                                customInput = ""
                            }
                        },
                        enabled = customInput.isNotBlank() && customMessages.size < 20,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PaleGold,
                            contentColor = NightBlue
                        )
                    ) {
                        Text("追加")
                    }
                }
            }
            if (customMessages.size >= 20) {
                item {
                    Text("上限（20件）に達しています", fontSize = 12.sp, color = MutedBlueGray)
                }
            }

            // 追加済み一覧
            items(customMessages) { msg ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = msg,
                        fontSize = 14.sp,
                        color = White,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { customMessages = customMessages.filterNot { it == msg } }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "削除",
                            tint = MutedBlueGray
                        )
                    }
                }
            }

            // 保存ボタン
            item {
                Button(
                    onClick = {
                        viewModel.saveSettings(goalName, targetDate, tone, customMessages)
                        onNavigateBack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PaleGold,
                        contentColor = NightBlue
                    )
                ) {
                    Text("保存", fontSize = 16.sp)
                }
            }
        }
    }

    // 日付選択ダイアログ
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialDateMillis
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        targetDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                            .format(dateFormatter)
                    }
                    showDatePicker = false
                }) {
                    Text("OK", color = PaleGold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("キャンセル", color = MutedBlueGray)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
