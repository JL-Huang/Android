package com.example.myapplication.MVPDemo;

//model层完成数据的读写等业务逻辑
public interface UserModel {
    void setID(int id);

    void setName(String name);

    int getID();

    UserBean load(int id);
}
