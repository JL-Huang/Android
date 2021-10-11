package com.example.myapplication.SQLiteDemo;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String createBookCommand="create table Book"+
            "(id integer primary key autoincrement,"+
            "author text,"+
            "price real,"+
            "name text)";
//    name是数据库名,factory一般传null
//    由于要传入数据库名，因此一般是一个Helper对应一个数据库
//    当程序中没有传入名字对应的数据库，执行getWritableDatabase就会创建一个并执行onCreate
//    当有但传入版本比现有版本高，执行getWritableDatabase就会执行onUpgrade
    public MySQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public MySQLiteHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        创建一个表，这里感觉不规范数据库的创建和表的创建应该分离
        db.execSQL(createBookCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
