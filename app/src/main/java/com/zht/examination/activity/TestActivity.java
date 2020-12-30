package com.zht.examination.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zht.examination.R;
import com.zht.examination.device.Reader;
import com.zht.examination.utils.MyLog;
import com.zht.examination.utils.OTGUtil;

public class TestActivity extends AppCompatActivity {

    private Reader reader;
    //手机是否成功连接驱动
    private boolean canGo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        try {
            OTGUtil.setOTGEnable(true);
            MyLog.v("OTGUtil","setOTGEnable 成功");
            reader = Reader.getInstance();
            MyLog.v("Reader",reader.isConnect()+"获得单例成功");
            //判断是否已经连接设备
            if (!reader.isConnect()) {
                //连接设备操作
                int connect = reader.connect();
                if (connect == 0) {
                    //TODO 连接成功
                    canGo = true;
                    MyLog.v("connect",reader.isConnect()+"连接成功");
                }else {
                    //TODO 连接失败
                    canGo = false;
                    MyLog.v("connect",reader.isConnect()+"连接失败");
                }
            }else {
                //TODO 已经连接了
                canGo = true;
                MyLog.v("connect",reader.isConnect()+"已经连接了");
            }
        }catch (Exception e){
            e.printStackTrace();
            MyLog.v("错误",e.getMessage());
        }
    }
}
