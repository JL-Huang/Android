package com.example.myapplication.SQLiteDemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.example.myapplication.R;

public class SQLiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        SQLiteOpenHelper sqLiteOpenHelper=new MySQLiteHelper(this,"Book.db",null,1);
        SQLiteDatabase db=sqLiteOpenHelper.getWritableDatabase();
//        通过ContentValues装载数据，指定表名往表里增加数据
        ContentValues cv1=new ContentValues();
        cv1.put("author","huang");
        cv1.put("price","9.9");
        cv1.put("name","myBook");
        db.insert("Book",null,cv1);
        ContentValues cv2=new ContentValues();
//        更改数据
        cv2.put("price","9.8");
//        第三个参数是where后面的部分，?是占位符号，由第四个参数填充
        db.update("Book",cv2,"name=?",new String[]{"myBook"});
//        删除数据
//        跟上边一个道理
        db.delete("Book","price>?",new String[]{"9"});
//        查询数据
//        table是表名，columns是查询的列名，selection是where约束条件，selectionArgs是提供占位符的值
//        groupBy，having，orderBy字面意思
        Cursor cursor=db.query("Book",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            while (cursor.moveToNext()){
                Double price=cursor.getDouble(cursor.getColumnIndex("name"));
            }
        }
        cursor.close();
        db.beginTransaction();
        try {
            db.delete("Book",null,null);
            db.insert("Book",null,cv1);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }
}