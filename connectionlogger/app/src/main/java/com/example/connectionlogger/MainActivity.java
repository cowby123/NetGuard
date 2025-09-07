package com.example.connectionlogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

// 主介面活動，負責顯示從服務傳來的連線日誌
public class MainActivity extends AppCompatActivity {
    // 用於顯示日誌文字的視圖
    private TextView logView;

    // 接收 ConnectionLoggerService 廣播的接收器
    private final BroadcastReceiver logReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 取得服務發送的日誌訊息
            String msg = intent.getStringExtra(ConnectionLoggerService.EXTRA_MESSAGE);
            if (msg != null) {
                // 將日誌訊息顯示在畫面上
                logView.append(msg + "\n");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logView = findViewById(R.id.logs); // 取得日誌顯示區域
        // 註冊接收器以接收來自服務的日誌廣播
        registerReceiver(logReceiver,
                new IntentFilter(ConnectionLoggerService.ACTION_LOG),
                Context.RECEIVER_NOT_EXPORTED);
        // 啟動連線記錄服務
        startService(new Intent(this, ConnectionLoggerService.class));
    }

    @Override
    protected void onDestroy() {
        // 活動銷毀時取消註冊接收器以避免洩漏
        unregisterReceiver(logReceiver);
        super.onDestroy();
    }
}
