package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.UrlDemo.UrlActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        findViewById<View>(R.id.button_jump).setOnClickListener {
        testURL()
        finish()
//        }
    }

    private fun testURL() {
        val intent = Intent(this@MainActivity, UrlActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}