package com.example.myapplication.EventBusDemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;

public class SecondEventBusActivity extends AppCompatActivity {
    private Button button_event_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_event_bus);
        button_event_send=findViewById(R.id.button_event_send);
        button_event_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                发送事件，这里就用到了自定义类MessageEvent
                EventBus.getDefault().post(new MessageEvent("hhh"));
//                发送粘性事件，就是在发送事件之后再订阅该事件也能收到该事件，即保存发出的信息直到接受者订阅
                EventBus.getDefault().postSticky(new MessageEvent("hhh"));
                finish();
            }
        });
    }
}