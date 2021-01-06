package com.zht.examination.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.zht.examination.R;
import com.zht.examination.device.ReadTag;
import com.zht.examination.device.Reader;
import com.zht.examination.utils.NumUtil;

import java.util.HashSet;

public class OneActivity extends AppCompatActivity implements View.OnClickListener {

    EditText selectEpc;
    Button startOrStop;
    Button empty;
    ProgressBar sign;
    Integer signNumber = 0;

    Reader reader = Reader.getInstance();
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        dealWithSet();
                        sign.setProgress(signNumber);
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }

        };
    }


    @Override
    public void onClick(View v) {
        /* 开始/停止扫描 */
        if (v.getId() == R.id.button_scan) {
            //检验输入框内数据是否合法
            if (!checkInput()) {
                return;
            }
            if (reader.isConnect()) {
                if (startOrStop.getText().equals("开始")) {
                    //设置扫描强度
                    reader.setPower(30);
                    reader.startRead(mHandler);
                    startOrStop.setText("停止");
                } else {
                    reader.stopRead();
                    startOrStop.setText("开始");
                }
            }
        }

        /* 清空数据 */
        if (v.getId() == R.id.button_empty) {
            reader.stopRead();
            signNumber = 0;
            mHandler.sendEmptyMessage(1);
            startOrStop.setText("开始");
        }
    }

    void init() {
        selectEpc = findViewById(R.id.editText_select);
        selectEpc.setHorizontallyScrolling(true);
        KeyListener listener = new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
                return chars;
            }

            @Override
            public int getInputType() {
                return 3;
            }
        };
        selectEpc.setKeyListener(listener);
        startOrStop = findViewById(R.id.button_scan);
        empty = findViewById(R.id.button_empty);
        sign = findViewById(R.id.bar_rssi);

        sign.setProgress(signNumber);

        startOrStop.setOnClickListener(this);
        empty.setOnClickListener(this);

    }

    public boolean checkInput() {
        String s = selectEpc.getText().toString();

        if (s == null || s.isEmpty()) {
            Toast.makeText(this, "标签号为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (s.length() != 12) {
            Toast.makeText(this, "标签号长度不对！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!NumUtil.isNumeric(s)) {
            Toast.makeText(this, "标签号格式错误！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void dealWithSet() {
        HashSet<ReadTag> data = reader.getData();
        for (ReadTag datum : data) {
            if (datum.getEpcId().equals(selectEpc.getText().toString())) {
                reader.isSound = true;
                signNumber = datum.getRssi();
            }
        }
    }
}
