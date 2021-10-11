package com.example.myapplication.MultiProcess.AIDLDemo.BinderPool;

import android.os.RemoteException;

public class AIDLImpl{
    static class SecurityImpl extends ISecurity.Stub {

        @Override
        public String encrypt(String content) throws RemoteException {
            char[] chars=content.toCharArray();
            for(char each:chars){
                each ^='^';
            }
            return new String(chars);
        }

        @Override
        public String decrypt(String content) throws RemoteException {
            return encrypt(content);
        }
    }
    static class ComputeImpl extends ICompute.Stub{

        @Override
        public int add(int a, int b) throws RemoteException {
            return a+b;
        }
    }
}
