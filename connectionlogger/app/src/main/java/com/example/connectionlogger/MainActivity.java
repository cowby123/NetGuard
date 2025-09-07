package com.example.connectionlogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView logView;

    private final BroadcastReceiver logReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(ConnectionLoggerService.EXTRA_MESSAGE);
            if (msg != null) {
                logView.append(msg + "\n");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logView = findViewById(R.id.logs);
        registerReceiver(logReceiver, new IntentFilter(ConnectionLoggerService.ACTION_LOG));
        startService(new Intent(this, ConnectionLoggerService.class));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(logReceiver);
        super.onDestroy();
    }
}
