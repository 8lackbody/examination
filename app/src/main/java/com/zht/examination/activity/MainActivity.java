package com.zht.examination.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.zht.examination.R;
import com.zht.examination.device.Reader;
import com.zht.examination.utils.OTGUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button goOne;
    private Button goAll;
    private Reader reader;
    //手机是否成功连接驱动
    private boolean canGo = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

    }

    /**
     * 初始化
     */
    private void init(){
        goOne = findViewById(R.id.select_button);
        goOne.setOnClickListener(this);
        goAll = findViewById(R.id.examination_button);
        goAll.setOnClickListener(this);

        OTGUtil.setOTGEnable(true);
        reader = Reader.getInstance();
        //判断是否已经连接设备
        if (!reader.isConnect()) {
            //连接设备操作
            int connect = reader.connect();
            if (connect == 0) {
                //TODO 连接成功
                canGo = true;
            }else {
                //TODO 连接失败
                canGo = false;
            }
        }else {
            //TODO 已经连接了
            canGo = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            System.out.println("-------------------------------");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.examination_button:
                //TODO
                intent = new Intent().setClass(MainActivity.this, AllActivity.class);
                startActivity(intent);
                break;
            case R.id.select_button:
                //TODO
                intent = new Intent().setClass(MainActivity.this, OneActivity.class);
                startActivity(intent);
                break;
        }
    }
}
