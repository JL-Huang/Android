package com.example.myapplication.UrlDemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_url.button_jump_url


class UrlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url)

        val url = "realsee://wakeup?source=REALSEE_TECH&projectName=name&vr_type=phone&device_type=1&action_type=project_create&appid=124&callbal_url=ssssss";
        val uri = Uri.parse (url);
        val intent = Intent(Intent.ACTION_VIEW, uri)
        button_jump_url.setOnClickListener {
            startActivity(intent)
        }
    }
}