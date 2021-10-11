package com.example.myapplication.EventBusDemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActivity extends AppCompatActivity {
    private TextView tv_message;
    private Button bt_message;
    private Button bt_subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        tv_message=findViewById(R.id.tv_message);
        bt_message=findViewById(R.id.bt_message);
        bt_subscription=findViewById(R.id.bt_subscription);
        bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventBusActivity.this,SecondEventBusActivity.class));
            }
        });
        bt_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                注册事件
                EventBus.getDefault().register(EventBusActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        取消事件订阅
        EventBus.getDefault().unregister(this);
    }
//    事件订阅者处理事件，通过Subscribe注解处理事情，并指定运行线程
/*       POSTING(默认):如果使用事件处理函数指定了线程模型为POSTING，那么该事件是在哪个线程 发布出来的，
        事件处理函数就会在哪个线程中运行，也就是说发布事件和接收事件在同一个线程中。
        在线 程模型为POSTING的事件处理函数中尽量避免执行耗时操作，因为它会阻塞事件的传递，甚至有可能会引 起ANR。*/
/*        MAIN:事件的处理会在UI线程中执行。事件处理的时间不能太长，长了会导致ANR。*/
/*        BACKGROUND:如果事件是在UI线程中发布出来的，那么该事件处理函数就会在新的线程中运行;
        如果事件本来就是在子线程中发布出来的，那么该事件处理函数直接在发布事件的线程中执行。
        在此事件 处理函数中禁止进行UI更新操作。*/
/*        ASYNC:无论事件在哪个线程中发布，该事件处理函数都会在新建的子线程中执行;
        同样，此事件 处理函数中禁止进行UI更新操作。*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent){
        tv_message.setText(messageEvent.getMessage());
    }
}