package com.xuliwen.mestest1.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.xuliwen.mestest1.aidl.Student;
import com.xuliwen.mestest1.utils.L;

public class MessengerService extends Service {


    private Handler workHandler;
    private Messenger serviceMessenger;


    private Handler.Callback callback=new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
           // objTest(msg);
            bundleTest(msg);
            int arg1=msg.arg1;
            int arg2=msg.arg2;
            int result=arg1+arg2;
            Message msgResult=Message.obtain();
            msgResult.arg1=result;
            try {
                msg.replyTo.send(msgResult);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return false;
        }

       void objTest(Message msg){
           L.l("MessengerService+ "+msg.obj.toString());
          /* if(msg.obj instanceof Student){
               Student student= (Student) msg.obj;
               L.l("MessengerService+ "+student.toString());
           }*/

        }


        void bundleTest(Message msg){
            L.l("MessengerService:bundle: "+msg.getData().getInt("bundleTestInt"));
            L.l("MessengerService:bundle: "+msg.getData().getString("bundleTestString"));
        }
    };




    public MessengerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread handlerThread=new HandlerThread("MessengerService");
        handlerThread.start();
        workHandler=new Handler(handlerThread.getLooper(),callback);
        serviceMessenger=new Messenger(workHandler);
    }

    @Override
    public IBinder onBind(Intent intent) {
      return serviceMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        workHandler.getLooper().quit();
    }
}
