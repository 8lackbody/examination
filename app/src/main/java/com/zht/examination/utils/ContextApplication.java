package com.zht.examination.utils;

import android.app.Application;
import android.content.Context;

public class ContextApplication extends Application {

    private static Context context;

    private int errorData;

    @Override
    public void onCreate() {
        super.onCreate();
        ContextApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ContextApplication.context;
    }

    public int getErrorData() {
        return errorData;
    }

    public void setErrorData(int errorData) {
        this.errorData = errorData;
    }
}
