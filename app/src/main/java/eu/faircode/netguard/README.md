# NetGuard Java 模組說明

此目錄包含 NetGuard Android 應用的主要 Java 原始碼。以下為每個檔案的簡要功能：

- **ActivityDns.java**：顯示解析過的 DNS 記錄並提供匯出功能。
- **ActivityForwardApproval.java**：在啟動埠轉發前向使用者請求同意。
- **ActivityForwarding.java**：管理埠轉發規則的介面。
- **ActivityLog.java**：呈現網路存取紀錄，可篩選與匯出。
- **ActivityMain.java**：主要控制介面，用來啟用/停用防火牆等功能。
- **ActivityPro.java**：處理進階功能與捐贈的應用程式內購買畫面。
- **ActivitySettings.java**：設定頁面與偏好設定匯入/匯出邏輯。
- **AdapterAccess.java**：用於顯示連線存取統計的資料轉接器。
- **AdapterDns.java**：DNS 記錄列表的轉接器，標記逾時項目。
- **AdapterForwarding.java**：顯示埠轉發設定的轉接器。
- **AdapterLog.java**：網路紀錄列表的轉接器，可解析主機與顯示圖示。
- **AdapterRule.java**：應用程式規則列表的 RecyclerView 轉接器。
- **Allowed.java**：儲存已允許的遠端位址與埠。
- **ApplicationEx.java**：自訂 Application，建立通知通道並攔截未處理例外。
- **DatabaseHelper.java**：SQLite 資料庫管理與對應通知機制。
- **DownloadTask.java**：背景下載檔案並顯示通知的 AsyncTask。
- **ExpandedListView.java**：會自動展開高度以顯示所有項目的 ListView。
- **Forward.java**：描述單一埠轉發規則的資料類別。
- **FragmentSettings.java**：載入偏好設定版面的 Fragment。
- **GlideHelper.java**：Glide 圖片載入庫的模組設定。
- **IAB.java**：Google Play 應用程式內購買的服務封裝。
- **IPUtil.java**：IP 位址與 CIDR 相關的工具方法。
- **Packet.java**：封裝擷取到的網路封包資訊。
- **PendingIntentCompat.java**：提供相容於不同 Android 版本的 PendingIntent 方法。
- **ReceiverAutostart.java**：開機或更新後自動啟動服務並升級設定。
- **ReceiverPackageRemoved.java**：移除應用時清除資料與通知。
- **ResourceRecord.java**：DNS 資源記錄的資料類別。
- **Rule.java**：描述每個應用程式的防火牆規則與相關狀態。
- **ServiceExternal.java**：下載外部 hosts 檔案等工作的 IntentService。
- **ServiceSinkhole.java**：核心 VPN 服務，負責攔截與過濾網路流量。
- **ServiceTileFilter.java**：快速設定方塊，用於切換封鎖/允許過濾功能。
- **ServiceTileGraph.java**：快速設定方塊，用於顯示或隱藏即時流量圖表。
- **ServiceTileLockdown.java**：快速設定方塊，用於切換鎖定模式。
- **ServiceTileMain.java**：快速設定方塊，用於啟用或停用 NetGuard。
- **SwitchPreference.java**：修正原生 SwitchPreference 行為的自訂元件。
- **Usage.java**：紀錄流量使用量的資料類別。
- **Util.java**：應用程式通用的輔助函式集合。
- **Version.java**：版本字串解析與比較工具。
- **WidgetAdmin.java**：處理桌面小工具的廣播與自動開關服務。
- **WidgetLockdown.java**：顯示鎖定狀態的小工具。
- **WidgetMain.java**：顯示服務啟用狀態的小工具。

