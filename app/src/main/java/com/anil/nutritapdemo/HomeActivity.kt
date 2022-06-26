package com.anil.nutritapdemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.anil.nutritapdemo.databinding.ActivityHomeBinding
import com.anil.nutritapdemo.databinding.ActivityMainBinding
import com.anil.nutritapdemo.databinding.ActivityOtpactivityBinding
import com.budiyev.android.codescanner.*
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var codeScanner: CodeScanner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermissionAndOpenCamera()
        setListeners()

    }

    private fun initializeCamera() {
        codeScanner = CodeScanner(this, binding.scannerView)
        codeScanner?.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            (BarcodeFormat.QR_CODE)
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false
        }
        setCameraCallbacks()
    }

    private fun setListeners() {
        binding.scannerView.setOnClickListener {
            codeScanner?.startPreview()
        }
        binding.btnStart.setOnClickListener {
            codeScanner?.startPreview()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        codeScanner?.releaseResources()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
                initializeCamera()
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    private fun setCameraCallbacks() {
        codeScanner?.apply {
            decodeCallback = DecodeCallback {
                runOnUiThread {
                    Toast.makeText(this@HomeActivity, "Scan result: ${it.text}", Toast.LENGTH_LONG)
                        .show()
                    binding.btnStart.text = "Scan Again"
                }
            }
            errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
                runOnUiThread {
                    Toast.makeText(
                        this@HomeActivity, "Camera initialization error: ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun checkPermissionAndOpenCamera() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("Permission: ", "Permission Available")
                initializeCamera()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> {
                Log.i("Permission: ", "Permission required, requesting permission")
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }
}