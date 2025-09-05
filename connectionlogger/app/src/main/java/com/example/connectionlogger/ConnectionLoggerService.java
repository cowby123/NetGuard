package com.example.connectionlogger;

import android.content.Intent;
import android.net.VpnService;
import android.os.ParcelFileDescriptor;
import android.util.Log;

public class ConnectionLoggerService extends VpnService {
    static {
        System.loadLibrary("netguard");
    }

    private native long jni_init(int sdk);
    private native void jni_start(long context, int loglevel);
    private native void jni_run(long context, int tun, boolean fwd53, int rcode);
    private native void jni_stop(long context);
    private native void jni_clear(long context);
    private native int[] jni_get_stats(long context);

    private long handle;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (handle == 0) {
            handle = jni_init(android.os.Build.VERSION.SDK_INT);
            Builder builder = new Builder();
            builder.addAddress("10.1.10.1", 32);
            builder.addRoute("0.0.0.0", 0);
            try {
                ParcelFileDescriptor pfd = builder.establish();
                if (pfd != null) {
                    jni_start(handle, 0);
                    jni_run(handle, pfd.getFd(), true, 0);
                } else {
                    Log.e("ConnectionLogger", "Failed to establish TUN");
                }
            } catch (Exception ex) {
                Log.e("ConnectionLogger", "Error starting VPN", ex);
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (handle != 0) {
            jni_stop(handle);
            jni_clear(handle);
            handle = 0;
        }
        super.onDestroy();
    }

    // Callbacks from native layer
    public void usage(Usage usage) {
        Log.i("ConnectionLogger", "usage: " + usage);
    }

    public void log(Packet packet, int connection, boolean interactive) {
        Log.i("ConnectionLogger", "packet=" + packet + " connection=" + connection + " interactive=" + interactive);
    }

    public static class Usage {
        @Override
        public String toString() {
            return "Usage{}";
        }
    }

    public static class Packet {
        @Override
        public String toString() {
            return "Packet{}";
        }
    }
}
