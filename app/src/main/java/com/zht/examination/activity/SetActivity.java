package com.zht.examination.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.zht.examination.R;

public class SetActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, Switch.OnCheckedChangeListener {

    String patternIp = "^(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}$";


    EditText ip;
    SeekBar power;
    Switch isVoice;
    Button save;
    TextView num;

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
        num = findViewById(R.id.textView2);
        save.setOnClickListener(this);

        preferences = getSharedPreferences("set", 0);
        editor = preferences.edit();

        num.setText(preferences.getString("power", "0"));

        power.setMax(30);
        power.setProgress(Integer.valueOf(preferences.getString("power", "0")));
        power.setOnSeekBarChangeListener(this);

        isVoice.setChecked(preferences.getBoolean("sound", false));
        isVoice.setOnCheckedChangeListener(this);

        ip.setText(preferences.getString("ip", "192.168.1.1"));
    }

    @Override
    public void onClick(View v) {
        String s = ip.getText().toString();

        if (s == null || s.isEmpty()) {
            Toast.makeText(this, "ip为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (s.matches(patternIp)) {
            editor.putString("ip", ip.getText().toString());
            editor.commit();
        } else {
            Toast.makeText(this, "ip格式错误！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        num.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        editor.putString("power", num.getText().toString());
        editor.commit();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        editor.putBoolean("sound", isChecked);
        editor.commit();
    }
}
