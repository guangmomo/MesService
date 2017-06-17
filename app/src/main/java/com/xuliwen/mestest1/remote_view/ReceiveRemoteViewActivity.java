package com.xuliwen.mestest1.remote_view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.xuliwen.mestest1.R;

public class ReceiveRemoteViewActivity extends AppCompatActivity {

    private static final String RECEIVER_ACTION="com.xuliwen.mestest1.remote_view.ReceiveRemoteViewActivity.BroadcastReceiver";

    private LinearLayout mRemoteViewParent;

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //无效
            RemoteViews remoteViews=intent.getParcelableExtra("remoteViews");
            int layoutId=getResources().getIdentifier("layout_remote_views","layout",getPackageName());
            View view=getLayoutInflater().inflate(layoutId,mRemoteViewParent,false);
            remoteViews.reapply(ReceiveRemoteViewActivity.this,view);
            mRemoteViewParent.addView(view);

//            RemoteViews remoteViews=intent.getParcelableExtra("remoteViews");
//            View view=remoteViews.apply(ReceiveRemoteViewActivity.this,mRemoteViewParent);
//            mRemoteViewParent.addView(view);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_remote_view);
        mRemoteViewParent= (LinearLayout) findViewById(R.id.activity_receive_remote_view);
        registerReceiver(receiver,new IntentFilter(RECEIVER_ACTION));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
