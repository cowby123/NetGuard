# NetGuard 原生程式庫

此資料夾包含 NetGuard VPN 引擎的 C 原始碼。這些程式碼透過 CMake 與 Android NDK 編譯成共享函式庫，
提供 NetGuard 防火牆所使用的使用者空間網路堆疊。

## 建置

該函式庫會隨 Android 應用程式一同建置。Gradle 會透過 [`app/CMakeLists.txt`](../../../../CMakeLists.txt) 呼叫 CMake，
將此目錄內的原始碼彙整成 `netguard` 共用函式庫。
建置 Android 應用程式（例如 `./gradlew assembleDebug`）時會依據 `app/build.gradle` 中的設定使用 NDK 編譯這些原生程式碼。

## 原始碼概覽

- **netguard.c / netguard.h** – JNI 進入點、SOCKS5 設定與紀錄等全域配置，以及共用結構與常數。
- **session.c** – 使用 `epoll` 追蹤並清理連線的事件迴圈。
- **ip.c** – 從 TUN 介面讀取封包並依協定分派 IPv4/IPv6 流量。
- **tcp.c** – 使用者空間 TCP 實作，包含狀態追蹤、逾時與選擇性 SOCKS5 代理支援。
- **udp.c** – 管理 UDP 連線，提供閒置偵測與使用量統計。
- **icmp.c** – 基礎的 ICMP/ICMPv6 處理與連線逾時邏輯。
- **dns.c** – 解析 DNS 訊息並擷取資源紀錄。
- **dhcp.c** – 試驗性的 DHCP 解析與回應產生。
- **tls.c** – 檢視 TLS ClientHello 封包以取得 SNI。
- **pcap.c** – 選用的封包擷取功能，可將流量寫入 PCAP 檔以供分析。
- **util.c** – 其他輔助函式，例如檢查碼計算、紀錄包裝器與字串工具。

所有檔案皆以 GNU 通用公共授權條款第 3 版或其後版本釋出。

## 使用方式

所有 `.c` 檔案會在建置時由 CMake 自動加入並編譯成 `libnetguard.so`。Java/Kotlin 層可透過:

```java
System.loadLibrary("netguard");
```

載入該共享函式庫並呼叫匯出的 JNI 函式。

共用的資料結構與函式宣告位於 `netguard.h`；其他模組可 `#include "netguard.h"` 以取得必要的宣告。

若要新增功能：

1. 在此目錄新增 `.c`/`.h` 檔案。
2. 在 `netguard.h` 宣告需要對外使用的函式或結構。
3. 確認 `app/CMakeLists.txt` 會包含新的 `.c` 檔案以便編譯。

## 在其他 Android 專案使用

若要在其他 Android 專案中利用這些原生程式碼取得應用程式連線資料，可依照下列步驟進行：

1. 建立支援 NDK 與 CMake 的新專案，並安裝相容的 Android NDK/CMake 版本。
2. 將本目錄 `netguard` 複製到新專案的 `app/src/main/jni/`。 
3. 在新專案的 `CMakeLists.txt` 中加入與本專案相同的 `add_library`、`include_directories` 與 `target_link_libraries` 設定，將來源編譯成 `libnetguard.so`。
4. 修改 `app/build.gradle` 啟用 `externalNativeBuild`，指定上述 `CMakeLists.txt` 並設置需要的 NDK 版本與 `abiFilters`。
5. 在 Java/Kotlin 層建立一個 `VpnService` 類別（可複製 `ServiceSinkhole.java` 或自訂類別），在其中宣告對應的 `native` 方法：

   ```java
   static {
       System.loadLibrary("netguard");
   }

   private native long jni_init(int sdk);
   private native void jni_start(long context, int loglevel);
   private native void jni_run(long context, int tun, boolean fwd53, int rcode);
   private native void jni_stop(long context);
   private native void jni_clear(long context);
   private native int[] jni_get_stats(long context);
   ```

6. 在 `VpnService` 啟動後，透過 `jni_init` 建立原生 context，將 `VpnService.Builder` 取得的 TUN 檔案描述符傳入 `jni_run` 啟動事件迴圈。
7. 實作 `usage(Usage usage)`、`log(Packet packet, int connection, boolean interactive)` 等回呼方法，以接收原生層回報的連線與流量資訊。
8. 停止服務時呼叫 `jni_stop` 與 `jni_clear` 釋放資源並結束原生執行緒。

