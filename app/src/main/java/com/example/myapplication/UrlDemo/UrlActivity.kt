package com.example.myapplication.UrlDemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_url.edit_url
import kotlinx.android.synthetic.main.activity_url.button_jump_url


class UrlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url)
        val url =
            "realsee://wakeup?source=T-D7517K7W&ticket=123456789&name=name&project_id=cyclops-EGWK2O1oREWVDOg4&vr_type=phone&device_type=1&is_shoot_16k=1&business_info=abcd&entity_name=&entity_code=&action_type=project_create&app_id=1234&callback_url=sinaweibo://";
        edit_url.setText(url);
        button_jump_url.setOnClickListener {
            val uri = Uri.parse(edit_url.text.toString());
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}

import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mediaProjectionManager: MediaProjectionManager
    private lateinit var mediaProjection: MediaProjection
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var virtualDisplay: VirtualDisplay

    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaProjectionManager =
            getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

        startBtn.setOnClickListener {
            if (!isRecording) {
                startRecording()
                startBtn.text = "Stop Recording"
            } else {
                stopRecording()
                startBtn.text = "Start Recording"
            }
            isRecording = !isRecording
        }
    }

    private fun startRecording() {
        val mediaProjectionIntent = mediaProjectionManager.createScreenCaptureIntent()
        startActivityForResult(mediaProjectionIntent, REQUEST_CODE)
    }

    private fun stopRecording() {
        mediaRecorder.stop()
        mediaRecorder.reset()
        virtualDisplay.release()
        mediaProjection.stop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data)
            startMediaRecorder()
        }
    }

    private fun startMediaRecorder() {
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val currentTimeStamp = dateFormat.format(Date())

        val videoFile =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "Recording_$currentTimeStamp.mp4")

        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder.setVideoEncodingBitRate(512 * 1000)
        mediaRecorder.setVideoFrameRate(30)
        mediaRecorder.setVideoSize(1280, 720)
        mediaRecorder.setOutputFile(videoFile.absolutePath)
        mediaRecorder.prepare()

        virtualDisplay = mediaProjection.createVirtualDisplay(
            "RecordingDisplay",
            1280,
            720,
            resources.displayMetrics.densityDpi,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            mediaRecorder.surface,
            null,
            null
        )

        mediaRecorder.start()
    }

    companion object {
        private const val REQUEST_CODE = 1001
    }
}
