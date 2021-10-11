// IOnNewBookArrivedListener.aidl
package com.example.myapplication.MultiProcess.AIDLDemo;

// Declare any non-default types here with import statements
import com.example.myapplication.MultiProcess.AIDLDemo.Book;
interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}