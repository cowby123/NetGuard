# NetGuard 中文說明

**NetGuard** 是一個免費且開源的 Android 無 Root 防火牆，透過系統的 VPN 服務攔截流量，讓使用者能精細控制每個應用程式對 Wi‑Fi 及行動數據的存取權限。

## 主要特性
- 無需取得 Root 權限即可使用
- 100% 開源，無追蹤、無分析
- 支援 Android 5.1 以上版本，目標 SDK 為 35
- 可個別允許或阻擋應用程式的 IPv4/IPv6、TCP/UDP 流量
- 支援分享網路（Tethering）
- 可設定螢幕開啟時自動允許、漫遊時自動阻擋
- 可選擇是否包含系統應用程式
- 支援通知與記錄流量、導出 PCAP
- 可選擇性使用 hosts 檔案進行廣告封鎖（如未從 Play 商店安裝）

進階（Pro）功能包括：
- 紀錄所有輸出流量並提供搜尋與篩選
- 針對每個應用程式設定允許或阻擋的 IP 地址
- 顯示即時網路速度通知
- 額外的佈景主題選項

## 下載
- [GitHub Releases](https://github.com/M66B/NetGuard/releases)
- [Google Play](https://play.google.com/store/apps/details?id=eu.faircode.netguard)

## 使用方法
1. 啟動應用程式，透過頂部的開關啟用防火牆。
2. 在列表中點擊 Wi‑Fi 或行動數據圖示以允許或阻擋某個應用程式。
3. 可於設定中切換「黑名單模式」（預設允許，僅阻擋特定應用程式）或「白名單模式」（預設阻擋，僅允許特定應用程式）。

## 建置指南
此專案使用 Gradle 與 Android Studio 建置，`app/build.gradle` 指定：
- `compileSdkVersion 35`
- `minSdkVersion 22`
- 需安裝對應版本的 Android NDK（`25.2.9519653`）

建置步驟：
1. 安裝 [Android Studio](https://developer.android.com/studio) 與相容的 Android NDK。
2. 以 Android Studio 開啟專案或執行 `./gradlew assembleDebug` 進行建置。
3. 若要發佈簽名版本，需要在根目錄提供 `keystore.properties`。

## 貢獻
- 接受翻譯及程式碼貢獻，翻譯可透過 [Crowdin](https://crowdin.com/project/netguard/) 進行。
- 建置與開發相關問題需自行排除，專案不提供個別支援。

## 授權
本專案以 [GNU GPL v3](http://www.gnu.org/licenses/gpl.txt) 授權釋出。

