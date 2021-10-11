package com.example.myapplication.DispatchDemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myapplication.R;

public class DispatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);

    }
    class DebugWindowButton extends androidx.appcompat.widget.AppCompatButton {
        public DebugWindowButton(Context context) {
            super(context);
        }

        public DebugWindowButton(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public DebugWindowButton(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            return super.onTouchEvent(event);
        }
    }
}