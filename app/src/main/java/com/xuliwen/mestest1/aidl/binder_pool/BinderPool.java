package com.xuliwen.mestest1.aidl.binder_pool;

import android.os.IBinder;
import android.os.RemoteException;

import com.xuliwen.mestest1.aidl.IBinderPool;

/**
 * Created by xlw on 2017/5/28.
 */

class BinderPool extends IBinderPool.Stub {
    private static final int BINDER_SPEAK = 0;
    private static final int BINDER_CALCULATE = 1;


    BinderPool() {
        super();
    }

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case BINDER_SPEAK: {
                binder = new Speak();
                break;
            }
            case BINDER_CALCULATE: {
                binder = new Calculate();
                break;
            }
            default:
                break;
        }

        return binder;
    }


}
