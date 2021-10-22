package com.social.presentation.post

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import com.social.presentation.base.BaseActivity
import com.social.databinding.ActivityPreviewCameraBinding
import com.social.utils.BitmapUtil
import com.social.utils.BitmapUtil.JPG_EXT
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.social.presentation.dialogfragments.DialogFragmentChooser
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.lang.Exception
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class ActivityCamera : BaseActivity<ActivityPreviewCameraBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityPreviewCameraBinding
        get() = ActivityPreviewCameraBinding::inflate

    private var imageCapture: ImageCapture? = null
    private lateinit var imageFile: File
    private var cameraExecutor: ExecutorService? = null

    override fun setup() {
        startCamera()
        binding.btnTakePicture.setOnClickListener { takePhoto() }
        imageFile = BitmapUtil.createTempImagePathFileUUID(this, Environment.DIRECTORY_PICTURES, JPG_EXT)
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun takePhoto() {

        // Get a stable reference of the modifiable image capture use case
        if (imageCapture == null) return

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(imageFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture?.takePicture(
                outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(imageFile)
                intent.putExtra(DialogFragmentChooser.PATH, savedUri.toString())
                this@ActivityCamera.setResult(RESULT_OK, intent)
                this@ActivityCamera.finish()
            }

            override fun onError(exception: ImageCaptureException) {
                setResult(RESULT_CANCELED, intent)
                finish()
            }
        })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({

            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = try {
                cameraProviderFuture.get()
            } catch (e: ExecutionException) {
                e.printStackTrace()
                return@addListener
            } catch (e: InterruptedException) {
                e.printStackTrace()
                return@addListener
            }

            // Preview
            val preview = Preview.Builder()
                    .build()
            imageCapture = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()

            preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                val camera: Camera = cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageCapture)
                binding.ivFlash.setOnClickListener {
                    if (camera.cameraInfo.hasFlashUnit()) {
                        camera.cameraInfo.torchState.value ?: return@setOnClickListener
                    }
                }
            } catch (exception: Exception) {
                // Camera error while unbind
                exception.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor!!.shutdown()
    }

}