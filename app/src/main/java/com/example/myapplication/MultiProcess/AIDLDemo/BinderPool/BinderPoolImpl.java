package com.example.myapplication.MultiProcess.AIDLDemo.BinderPool;

import android.os.IBinder;
import android.os.RemoteException;

public class BinderPoolImpl extends IBinderPool.Stub {
    @Override
    public IBinder queryBinder(int BinderCode) throws RemoteException {
        IBinder binder = null;
        switch (BinderCode) {
            case 0: {
                binder = new AIDLImpl.SecurityImpl();
            }
            case 1: {
                binder = new AIDLImpl.ComputeImpl();
            }
        }
        return binder;
    }
}
