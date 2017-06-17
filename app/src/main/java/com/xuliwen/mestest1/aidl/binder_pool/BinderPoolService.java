package com.xuliwen.mestest1.aidl.binder_pool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BinderPoolService extends Service {

    private Binder mBinderPool = new BinderPool(); // 1
    private int pid = android.os.Process.myPid();
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("cylog", "当前进程ID为："+pid+"----"+"客户端与服务端连接成功，服务端返回BinderPool.BinderPoolImpl 对象");
        Log.d("cylog", "当前线程ID为："+Thread.currentThread().getId()+"----"+"客户端与服务端连接成功，服务端返回BinderPool.BinderPoolImpl 对象");
        return mBinderPool;
    }
}
