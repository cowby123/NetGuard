# ConnectionLogger

ConnectionLogger 是一個示範專案，展示如何以 NetGuard 的 VPN 引擎擷取並記錄裝置上的所有網路連線。

## 流程概述

1. `MainActivity` 啟動後立即啟動 `ConnectionLoggerService`。
2. `ConnectionLoggerService` 擴充自 Android `VpnService`，在 `onStartCommand` 中：
   - 透過 JNI 載入 `libnetguard` 原生程式庫並呼叫 `jni_init` 建立原生 context。
   - 使用 `VpnService.Builder` 建立虛擬的 TUN 介面，設定位址和路由。
   - 成功建立 TUN 後，呼叫 `jni_start` 啟動記錄器，接著以檔案描述符與 `jni_run` 執行事件迴圈。
   - 原生層透過回呼 `usage(Usage)` 與 `log(Packet, int, boolean)` 回報流量統計與封包詳細資料，並寫入 Android 的 `Logcat`。
3. 服務被停止時，會依序呼叫 `jni_stop` 與 `jni_clear` 釋放資源。

## 主要功能

- 建立僅供記錄用途的 VPN 連線，所有流量會被導向使用者空間。
- 利用 NetGuard 的原生程式庫解析 TCP、UDP、DNS 等協定，並將每個封包及連線狀態透過 Java 回呼輸出。
- 示範如何在 Android 應用程式中整合 C/C++ 原生程式碼（透過 CMake 與 NDK）。

## 建置

在專案根目錄執行：

```
./gradlew -p connectionlogger assembleDebug
```

即可編譯出 `ConnectionLogger` 的 APK，過程中會同時編譯 `app/src/main/jni/netguard` 底下的原生程式碼。

## 目錄結構

- `app/src/main/java/com/example/connectionlogger` – Java 入口點與服務。
- `app/src/main/jni/netguard` – 來自 NetGuard 的原生程式庫。
- `app/src/main/res` – 介面與資源檔案。
