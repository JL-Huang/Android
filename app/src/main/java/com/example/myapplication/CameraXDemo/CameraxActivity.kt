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
//        ????????????????????????????????????????????????
        val cameraProviderFuture=ProcessCameraProvider.getInstance(this)
//        ????????????????????????????????????????????????????????????????????????
        cameraProviderFuture.addListener(Runnable {
//            ??????cameraProviderFutur???get??????????????????cameraProvider??????
            val cameraProvider:ProcessCameraProvider=cameraProviderFuture.get()
//            ??????????????????preview
            preview=Preview.Builder().build()
//            ??????????????????
            val cameraSelector=CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
            try {
//                ???????????????
                cameraProvider.unbindAll()
//                ???????????????????????????preview???CameraxActivity?????????CameraxActivity???????????????
                camera=cameraProvider.bindToLifecycle(this,cameraSelector,preview)
//                ???preview??????????????????PreviewView????????????SurfaceProvider????????????????????????
                preview?.setSurfaceProvider(camera_view.createSurfaceProvider(camera?.cameraInfo))
            }catch (e:Exception){

            }
//            ?????????????????????????????????executor
        },ContextCompat.getMainExecutor(this))
    }
}