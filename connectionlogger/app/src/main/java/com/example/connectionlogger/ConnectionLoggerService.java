package com.example.connectionlogger;

import android.content.Intent;
import android.net.VpnService;
import android.os.ParcelFileDescriptor;
import android.util.Log;

// 透過 VPNService 監聽並紀錄網路連線的背景服務
public class ConnectionLoggerService extends VpnService {
    // 廣播動作與附加訊息鍵值
    public static final String ACTION_LOG = "com.example.connectionlogger.LOG";
    public static final String EXTRA_MESSAGE = "message";
    static {
        // 載入原生函式庫
        System.loadLibrary("netguard");
    }

    // 與原生層互動的函式宣告
    private native long jni_init(int sdk);
    private native void jni_start(long context, int loglevel);
    private native void jni_run(long context, int tun, boolean fwd53, int rcode);
    private native void jni_stop(long context);
    private native void jni_clear(long context);
    private native int[] jni_get_stats(long context);

    // 保存原生層回傳的操作代號
    private long handle;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (handle == 0) {
            // 初始化原生層並建立 TUN 介面
            handle = jni_init(android.os.Build.VERSION.SDK_INT);
            Builder builder = new Builder();
            builder.addAddress("10.1.10.1", 32);
            builder.addRoute("0.0.0.0", 0);
            try {
                ParcelFileDescriptor pfd = builder.establish();
                if (pfd != null) {
                    // 開始並運行記錄服務
                    jni_start(handle, 0);
                    jni_run(handle, pfd.getFd(), true, 0);
                } else {
                    // 建立 TUN 失敗
                    Log.e("ConnectionLogger", "Failed to establish TUN");
                }
            } catch (Exception ex) {
                Log.e("ConnectionLogger", "Error starting VPN", ex);
            }
        }
        // 若服務被系統終止，嘗試重啟
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (handle != 0) {
            // 停止原生層並清理資源
            jni_stop(handle);
            jni_clear(handle);
            handle = 0;
        }
        super.onDestroy();
    }

    // 以下為原生層回呼
    public void usage(Usage usage) {
        String msg = "usage: " + usage;
        Log.i("ConnectionLogger", msg);
        Intent intent = new Intent(ACTION_LOG);
        intent.putExtra(EXTRA_MESSAGE, msg);
        sendBroadcast(intent);
    }

    public void log(Packet packet, int connection, boolean interactive) {
        String msg = "packet=" + packet + " connection=" + connection + " interactive=" + interactive;
        Log.i("ConnectionLogger", msg);
        Intent intent = new Intent(ACTION_LOG);
        intent.putExtra(EXTRA_MESSAGE, msg);
        sendBroadcast(intent);
    }

    // 使用量資訊模型
    public static class Usage {
        @Override
        public String toString() {
            return "Usage{}";
        }
    }

    // 封包資訊模型
    public static class Packet {
        @Override
        public String toString() {
            return "Packet{}";
        }
    }
}
