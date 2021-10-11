package com.example.myapplication.IntentDemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class BeforeActivity extends AppCompatActivity {
Button button;
TextView textView;
int CODE_ATRER=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before);
        button=findViewById(R.id.button_before);
        textView=findViewById(R.id.text_before);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BeforeActivity.this,AfterActivity.class);
                startActivityForResult(intent,CODE_ATRER);
            }
        });
    }
//    隐式启动
    private void intent_test(){
        Intent intent=new Intent("com.example.myapplication.ACTION_START");
        intent.addCategory("com.example.myapplication.MY_CATEGORY");
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE_ATRER){
            textView.setText(data.getStringExtra("respond"));
        }
    }
}