# saku-countdown

「朔」からの一言と経過日数を表示するカウントダウンアプリ。

## 概要

- アプリ名：saku-countdown
- パッケージ名：`com.saku.countdown`
- 最小 SDK：26（Android 8.0）
- ターゲット SDK：34
- 言語：Kotlin
- UI：Jetpack Compose（Material 3）
- ビルド：Gradle（Kotlin DSL）

## 画面構成

- **メイン画面**：経過日数（○日目）、朔からの一言メッセージ、目標名・目標日を表示
- **設定画面**：目標名、目標日（DatePicker）、メッセージの雰囲気（標準 / やさしめ / 文学的 / ツンデレ）を設定

## ビルド手順

### 前提

- Android Studio（最新の安定版を推奨）
- JDK 17
- Android SDK（API 34）

### 手順

1. Android Studio でこのディレクトリを開く（`File > Open`）。
2. Gradle の同期を待つ（初回は依存関係のダウンロードに時間がかかる場合がある）。
3. エミュレータまたは実機（Android 8.0 以上）を選択して実行ボタンを押す。

### コマンドラインでビルドする場合

`gradlew` ラッパーが無い場合は、まずローカルの Gradle でラッパーを生成するか、
Android Studio で一度プロジェクトを開いてラッパーを再生成してください。

```bash
# ラッパー生成（Gradle 8.9 がインストールされている場合）
gradle wrapper --gradle-version 8.9

# ビルド
./gradlew assembleDebug
```

## データ保存

アプリの状態は SharedPreferences のキー `saku_data` に JSON 文字列として保存されます。

```json
{
  "goalName": "",
  "targetDate": "",
  "startDate": "",
  "todayDayCount": 0,
  "lastMessage": "",
  "lastMessageIndex": -1,
  "lastUpdateDate": "",
  "messageTone": "standard"
}
```

## プリセットメッセージ

`app/src/main/java/com/saku/countdown/data/PresetMessages.kt` に60個のプリセット
メッセージを埋め込む想定です。現在はサンプル5個（残り55個は TODO）です。

## プロジェクト構成

```
app/src/main/java/com/saku/countdown/
├── MainActivity.kt          # 単一 Activity（Navigation で画面切替）
├── MainViewModel.kt         # 全状態管理
├── data/
│   ├── SakuData.kt          # データクラス + JSON 保存・読込
│   └── PresetMessages.kt    # プリセットメッセージ定数
└── ui/
    ├── MainScreen.kt        # メイン画面
    ├── SettingsScreen.kt    # 設定画面
    └── theme/               # 配色・テーマ
```
