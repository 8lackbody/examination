package com.zht.examination.utils;

import android.app.Application;
import android.content.Context;

import com.zht.examination.device.ErrorData;

import java.util.ArrayList;
import java.util.List;

public class ContextApplication extends Application {

    private static Context context;

    private List<ErrorData> errorData;

    @Override
    public void onCreate() {
        super.onCreate();
        errorData = new ArrayList<>();
        System.out.println("----现在就创建了错误数组------");
        ContextApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ContextApplication.context;
    }

    public List<ErrorData> getErrorData() {
        return errorData;
    }

    public void setErrorData(List<ErrorData> errorData) {
        this.errorData = errorData;
    }
}
