package com.example.myapplication.进度条

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R

class ProgressActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)
        var view = findViewById<圆形进度条>(R.id.progressbar)
        view.progress = 30
    }
}