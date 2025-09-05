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
