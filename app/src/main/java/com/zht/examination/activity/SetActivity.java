package com.zht.examination.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.zht.examination.R;

public class SetActivity extends AppCompatActivity implements View.OnClickListener {

    EditText ip;
    SeekBar power;
    Switch isVoice;
    Button save;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    void init() {
        ip = findViewById(R.id.edit_ip);
        power = findViewById(R.id.seekBar);
        isVoice = findViewById(R.id.isVoice);
        save = findViewById(R.id.save);
        save.setOnClickListener(this);

        preferences = getSharedPreferences("set", 0);
        editor = preferences.edit();
    }

    @Override
    public void onClick(View v) {
        //TODO 保存设置
    }
}
