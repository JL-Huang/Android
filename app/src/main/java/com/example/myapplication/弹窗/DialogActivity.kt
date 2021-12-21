package com.example.myapplication.弹窗

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        val dialogFrag =DiaLogFragDemo(object : DiaLogFragDemo.SubmitCallBack{
            override fun callBack() {
                TODO("Not yet implemented")
            }

        })
        dialogFrag.show(this!!.supportFragmentManager,"")
    }
}
