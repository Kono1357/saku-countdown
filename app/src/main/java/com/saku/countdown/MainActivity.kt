package com.saku.countdown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saku.countdown.ui.HistoryScreen
import com.saku.countdown.ui.MainScreen
import com.saku.countdown.ui.SettingsScreen
import com.saku.countdown.ui.theme.SakuCountdownTheme
import kotlinx.coroutines.delay

/**
 * 単一 Activity。Compose Navigation で3画面（メイン・設定・履歴）を切り替える。
 * Android 12+ の SplashScreen API で三日月アイコンを表示し、1.5秒後にメインへ遷移する。
 */
class MainActivity : ComponentActivity() {

    // スプラッシュを 1.5 秒保持するためのフラグ
    private val keepSplash = mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplash.value }

        setContent {
            SakuCountdownTheme {
                // 1.5 秒後にスプラッシュを閉じてメインを表示する
                LaunchedEffect(Unit) {
                    delay(1500)
                    keepSplash.value = false
                }

                // 復帰（ON_RESUME）時に日数・メッセージを再計算する
                val viewModel: MainViewModel = viewModel()
                val lifecycleOwner = LocalLifecycleOwner.current
                DisposableEffect(lifecycleOwner) {
                    val observer = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_RESUME) {
                            viewModel.refresh()
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)
                    onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "main"
                    ) {
                        composable("main") {
                            MainScreen(
                                onNavigateToSettings = { navController.navigate("settings") },
                                onNavigateToHistory = { navController.navigate("history") }
                            )
                        }
                        composable("settings") {
                            SettingsScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("history") {
                            HistoryScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
