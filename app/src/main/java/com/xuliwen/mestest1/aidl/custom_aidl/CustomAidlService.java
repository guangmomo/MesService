package com.xuliwen.mestest1.aidl.custom_aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class CustomAidlService extends Service {
    private static final int TRANSACTION_add=0;
    private static final String DESCRIPTOR = "com.xuliwen.mestest1.aidl.custom_aidl.CustomAidlService";
    private IBinder aidlBinder=new AidlBinder();

    public CustomAidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return aidlBinder;
    }


    private class AidlBinder extends Binder{
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

            /**
             * 通过uid来做权限验证,在onBind方法中不能正确的得到调用方的uid
             */
            String packageName=null;
            String[] pkgArray=getPackageManager().getPackagesForUid(Binder.getCallingUid());
            if(pkgArray!=null &&  pkgArray.length>0){
                packageName=pkgArray[0];
            }
            if(!"com.xuliwen.mesclien".equals(packageName)){
                return false;
            }

            switch (code){
                 case TRANSACTION_add:
                     data.enforceInterface(DESCRIPTOR);
                     int arg1=data.readInt();
                     int arg2=data.readInt();
                     int result=this.add(arg1,arg2);
                     reply.writeNoException();
                     reply.writeInt(result);
                     return true;
             }
            return super.onTransact(code, data, reply, flags);
        }

        private int add(int arg1,int arg2){
            return arg1+arg2;
        }
    }
}
