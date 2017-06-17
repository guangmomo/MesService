package com.xuliwen.mestest1;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xuliwen.mestest1.aidl.common_aidl.RemoteService;
import com.xuliwen.mestest1.utils.L;

public class MainActivity extends AppCompatActivity {

    private RemoteService.MyBinder myBinder;

    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder= (RemoteService.MyBinder) service;
                myBinder.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.l("MesService UI ThreadId :"+Thread.currentThread().getId());
      //  startService(new Intent(this,RemoteService.class));
      //  bindService(new Intent(this,RemoteService.class),serviceConnection,BIND_AUTO_CREATE);
    }
}
