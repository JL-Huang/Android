// ISecurity.aidl
package com.example.myapplication.MultiProcess.AIDLDemo.BinderPool;

// Declare any non-default types here with import statements

interface ISecurity {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String encrypt(String content);
    String decrypt(String content);
}