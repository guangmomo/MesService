package com.xuliwen.mestest1.aidl.common_aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

import com.xuliwen.mestest1.aidl.MyAIDLService;
import com.xuliwen.mestest1.aidl.Student;
import com.xuliwen.mestest1.utils.L;

public class RemoteService extends Service {

    private MyAIDLService.Stub stub=new MyAIDLService.Stub() {
        @Override
        public int add(int arg1, int arg2) throws RemoteException {
            return arg1+arg2;
        }

        @Override
        public int minus(int arg1, int arg2) throws RemoteException {
            return arg1-arg2;
        }

        @Override
        public String inStudentInfo(Student student) throws RemoteException {
            return student.toString();
        }

        @Override
        public String outStudentInfo(Student student) throws RemoteException {
            student.setAge(40);
            student.setName("王治郅");
            return student.toString();
        }

        @Override
        public String inOutStudentInfo(Student student) throws RemoteException {
            return student.toString();
        }
    };

    private Binder myBinder=new MyBinder();

    public RemoteService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        L.l(String.valueOf(Process.myPid()));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return stub;
    }

   public class MyBinder extends Binder{
        public void startDownload() {
            L.l("startDownload() executed");
        }
    }
}
