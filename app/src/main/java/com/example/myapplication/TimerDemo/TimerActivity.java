package com.example.myapplication.TimerDemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {
    private Button button_timing;
    private final int MAX_TIME=11;
    Timer timer=new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        button_timing=findViewById(R.id.button_timing);
        timer.schedule(task,0,1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TimerActivity.this, "延迟5s", Toast.LENGTH_SHORT).show();
            }
        },5000);
    }
    TimerTask task=new TimerTask() {
        final int[] timeCounter = {MAX_TIME};
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    button_timing.setText(""+timeCounter[0]);
                    timeCounter[0]--;
                    if(timeCounter[0]<1){
                        timer.cancel();

                    }
                }
            });
        }
    };
}