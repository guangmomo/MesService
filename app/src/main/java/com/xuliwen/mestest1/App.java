package com.xuliwen.mestest1;

/**
 * Created by xlw on 2017/6/1.
 */

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this.getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}