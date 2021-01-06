package com.zht.examination.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zht.examination.R;
import com.zht.examination.adapter.AllAdapter;
import com.zht.examination.device.ReadTag;
import com.zht.examination.device.Reader;
import com.zht.examination.utils.NumUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class AllActivity extends AppCompatActivity implements View.OnClickListener {

    EditText startEpc;
    Integer startNum;
    EditText endEpc;
    Integer endNum;
    TextView selectNumber;
    RecyclerView recyclerView;
    Button startOrStop;
    Button submit;
    Button empty;
    AllAdapter allAdapter;

    Reader reader = Reader.getInstance();
    List<ReadTag> listData;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        dealWithSet();
                        selectNumber.setText("" + listData.size());
                        allAdapter.setList(listData);
                        allAdapter.notifyDataSetChanged();
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

        /* 扫描按钮 */
        if (v.getId() == R.id.startOrStop) {

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
        if (v.getId() == R.id.empty) {
            reader.stopRead();
            startOrStop.setText("开始");
            listData.clear();
            mHandler.sendEmptyMessage(1);
        }

        /* 提交数据 */
        if (v.getId() == R.id.submit) {
            //TODO 提交数据
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
        startEpc.setKeyListener(listener);
        endEpc.setKeyListener(listener);
        startEpc.setHorizontallyScrolling(true);
        endEpc.setHorizontallyScrolling(true);

        //加载表格
        listData = new ArrayList<>();
        allAdapter = new AllAdapter(this, listData);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(allAdapter);
        selectNumber.setText("" + listData.size());
        startOrStop.setOnClickListener(this);
        submit.setOnClickListener(this);
        empty.setOnClickListener(this);

    }


    public boolean checkInput() {

        String s = startEpc.getText().toString();
        String e = endEpc.getText().toString();

        // 不为空，长度为12，为纯数字，两个字符前六位相同
        if (s == null || e == null || e.isEmpty() || s.isEmpty()) {
            Toast.makeText(this, "标签号为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (s.length() != 12 || e.length() != 12) {
            Toast.makeText(this, "标签号长度不对！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!(NumUtil.isNumeric(s) && NumUtil.isNumeric(e))) {
            Toast.makeText(this, "标签号格式错误！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!(s.substring(0,6).equals(e.substring(0,6)))) {
            Toast.makeText(this, "标签号不匹配！", Toast.LENGTH_SHORT).show();
            return false;
        }
        startNum = Integer.valueOf(s.substring(6));
        endNum = Integer.valueOf(e.substring(6));
        if (startNum >= endNum) {
            Toast.makeText(this, "搜索范围错误！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 将每次扫描的数据和开始标签和结束标签对比加入显示data中
     */
    public void dealWithSet() {
        HashSet<ReadTag> data = reader.getData();
        for (ReadTag datum : data) {
            //是否已经包含在list内
            boolean contains = listData.contains(datum);
            //是不是在查找的标签中
            boolean falg = false;
            Integer epc = Integer.valueOf(datum.getEpcId().substring(6));
            if(epc>= startNum && epc<= endNum){
                falg = true;
                reader.isSound = true;
            }
            if (falg && !contains) {
                listData.add(datum);
            }
        }
        Collections.sort(listData, new Comparator<ReadTag>() {
            @Override
            public int compare(ReadTag o1, ReadTag o2) {
                return o1.getEpcId().compareTo(o2.getEpcId());
            }
        });

    }


}
