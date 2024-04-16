package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.EventBusDemo.EventBusActivity;
import com.example.myapplication.MultiProcess.AIDLDemo.BookManagerActivity;
import com.example.myapplication.MultiProcess.Messenger.MessengerActivity;
import com.example.myapplication.RxJavaDemo.RxJavaActivity;
import com.example.myapplication.UrlDemo.UrlActivity;
import com.example.myapplication.进度条.ProgressActivity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button_jump);
        TextView helloWorld = findViewById(R.id.hello_world);
        EditText editText = findViewById(R.id.edit_url);
//        Set s=new HashSet();
//        Toast.makeText(this,String.valueOf(s instanceof Set),Toast.LENGTH_LONG).show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent= new Intent(MainActivity.this, ProgressActivity.class);
                    startActivity(intent);
            }
        });
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        }
    }

    private void testURL() {
        Intent intent= new Intent(MainActivity.this, UrlActivity.class);
        startActivity(intent);
    }


}