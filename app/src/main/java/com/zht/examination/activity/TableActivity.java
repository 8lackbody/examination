package com.zht.examination.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zht.examination.R;
import com.zht.examination.adapter.ErrorAdapter;
import com.zht.examination.device.ErrorData;
import com.zht.examination.utils.ContextApplication;

import java.util.List;

public class TableActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ErrorAdapter errorAdapter;
    List<ErrorData> errorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.error_table);
        //加载表格

        errorData = ((ContextApplication) getApplication()).getErrorData();

        errorAdapter = new ErrorAdapter(this, errorData);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(errorAdapter);

    }

}
