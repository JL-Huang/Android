package com.example.myapplication.MVPDemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;
//activity充当View的角色，不再处理逻辑
public class UserActivity extends AppCompatActivity implements View.OnClickListener,UserView {
    UserPresenter presenter;
    EditText editTextId,editTextName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
//        id=findViewById()
//        name=findViewById()
//        this指userview实现类，实现了presenter与view的绑定
        presenter=new UserPresenter(this);
    }
//view的变化触发presenter的动作，presenter的动作中同时对model与view进行的操作
//    view与presenter之间只传递事件和结果
    @Override
    public void onClick(View v) {
        presenter.saveUser(showID(), showName());
        presenter.loadUser(showID());
    }
//以下都是对view的操作
    @Override
    public int showID() {
        return 0;
    }

    @Override
    public String showName() {
        return null;
    }

    @Override
    public void setName(String name) {
        editTextName.setText(name);
    }
}