// IBookManager.aidl
package com.example.myapplication.MultiProcess.AIDLDemo;

// Declare any non-default types here with import statements

import com.example.myapplication.MultiProcess.AIDLDemo.Book;

import com.example.myapplication.MultiProcess.AIDLDemo.IOnNewBookArrivedListener;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}