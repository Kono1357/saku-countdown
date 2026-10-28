package com.saku.countdown.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.saku.countdown.R

// Noto Serif JP（res/font の可変フォント）。Normal と Bold を同じ可変フォントに割り当てる。
val NotoSerifJP = FontFamily(
    Font(R.font.noto_serif_jp, weight = FontWeight.Normal),
    Font(R.font.noto_serif_jp, weight = FontWeight.Bold)
)

// 夜空をイメージしたダーク配色
private val SakuColorScheme = darkColorScheme(
    primary = PaleGold,
    onPrimary = NightBlue,
    secondary = MutedBlueGray,
    onSecondary = White,
    background = NightBlue,
    onBackground = White,
    surface = SoftIndigo,
    onSurface = White
)

// アプリ全体を Noto Serif JP にするための Typography
private val SakuTypography = Typography(
    bodyLarge = TextStyle(fontFamily = NotoSerifJP, fontSize = 16.sp),
    bodyMedium = TextStyle(fontFamily = NotoSerifJP, fontSize = 14.sp),
    bodySmall = TextStyle(fontFamily = NotoSerifJP, fontSize = 12.sp),
    titleLarge = TextStyle(fontFamily = NotoSerifJP, fontSize = 22.sp),
    titleMedium = TextStyle(fontFamily = NotoSerifJP, fontSize = 16.sp),
    titleSmall = TextStyle(fontFamily = NotoSerifJP, fontSize = 14.sp),
    headlineMedium = TextStyle(fontFamily = NotoSerifJP),
    headlineSmall = TextStyle(fontFamily = NotoSerifJP),
    labelLarge = TextStyle(fontFamily = NotoSerifJP),
    labelMedium = TextStyle(fontFamily = NotoSerifJP),
    labelSmall = TextStyle(fontFamily = NotoSerifJP)
)

/** アプリ全体のテーマ。 */
@Composable
fun SakuCountdownTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SakuColorScheme,
        typography = SakuTypography,
        content = content
    )
}
