package com.zht.examination.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.zht.examination.R;
import com.zht.examination.device.Reader;

public class OneActivity extends AppCompatActivity implements View.OnClickListener {

    EditText selectEpc;
    Button startOrStop;
    Button empty;
    ProgressBar sign;

    Reader reader = Reader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }


    @Override
    public void onClick(View v) {
        /* 开始/停止扫描 */
        if (v.getId() == R.id.button_scan) {

        }

        /* 清空数据 */
        if (v.getId() == R.id.button_empty) {

        }
    }

    void init() {
        selectEpc = findViewById(R.id.editText_select);
        startOrStop = findViewById(R.id.button_scan);
        empty = findViewById(R.id.button_empty);
        sign = findViewById(R.id.bar_rssi);

        startOrStop.setOnClickListener(this);
        empty.setOnClickListener(this);

    }
}
