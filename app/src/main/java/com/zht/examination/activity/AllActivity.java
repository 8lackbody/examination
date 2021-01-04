package com.zht.examination.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.zht.examination.R;
import com.zht.examination.device.Reader;

public class AllActivity extends AppCompatActivity implements View.OnClickListener {

    EditText startEpc;
    EditText endEpc;
    TextView selectNumber;
    RecyclerView recyclerView;
    Button startOrStop;
    Button submit;
    Button empty;

    Reader reader = Reader.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    @Override
    public void onClick(View v) {

        /* 扫描按钮 */
        if (v.getId() == R.id.startOrStop) {
            if (startOrStop.getText().equals("开始扫描")) {
                //TODO 开始扫描
                if (reader.isConnect()) {
                    reader.setPower(30);
                    reader.startRead();
                }
            } else {
                //TODO 停止扫描
            }
        }

        /* 清空数据 */
        if (v.getId() == R.id.empty) {

        }

        /* 提交数据 */
        if (v.getId() == R.id.submit) {

        }
    }

    void init() {
        startEpc = findViewById(R.id.editText_select);
        endEpc = findViewById(R.id.editText_endId);
        selectNumber = findViewById(R.id.tx_num);
        recyclerView = findViewById(R.id.list);
        startOrStop = findViewById(R.id.startOrStop);
        submit = findViewById(R.id.submit);
        empty = findViewById(R.id.empty);

        startOrStop.setOnClickListener(this);
        submit.setOnClickListener(this);
        empty.setOnClickListener(this);

    }
}
