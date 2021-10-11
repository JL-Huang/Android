package com.example.myapplication.CameraXDemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_camerax.*
import java.io.File
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraxActivity : AppCompatActivity() {
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camerax)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }else{

        }
        camera_capture_button.setOnClickListener { takePhoto() }
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

    }
//    private fun startCamera() {
//            // TODO
//    }

    private fun takePhoto() {
            // TODO
    }
    fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }
    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
    private fun startCamera(){
//        A singleton which can be used to bind the lifecycle of cameras to any {@link LifecycleOwner}
//        用于相机生命周期与其拥有者的绑定
        val cameraProviderFuture=ProcessCameraProvider.getInstance(this)
//        为其设置一个监听器？监听什么，为什么要开一个线程
        cameraProviderFuture.addListener(Runnable {
//            通过cameraProviderFutur的get方法创建一个cameraProvider实例
            val cameraProvider:ProcessCameraProvider=cameraProviderFuture.get()
//            创建一个空白preview
            preview=Preview.Builder().build()
//            选择后摄像头
            val cameraSelector=CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
            try {
//                先确认解绑
                cameraProvider.unbindAll()
//                后将选择的摄像头及preview与CameraxActivity绑定，CameraxActivity即为拥有者
                camera=cameraProvider.bindToLifecycle(this,cameraSelector,preview)
//                往preview填充内容，在PreviewView创建一个SurfaceProvider，传入摄像头信息
                preview?.setSurfaceProvider(camera_view.createSurfaceProvider(camera?.cameraInfo))
            }catch (e:Exception){

            }
//            监听器第二个参数是一个executor
        },ContextCompat.getMainExecutor(this))
    }
}