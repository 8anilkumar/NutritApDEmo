package com.anil.nutritapdemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.anil.nutritapdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
    }

    private fun initListener() {
        binding.btnSubmit.setOnClickListener {
            val mNumber = binding.edMobileNumber.text.trim().toString()
            if (mNumber.isNotEmpty()) {
                if (mNumber.length == 10) {
                    startActivity(Intent(this, OTPActivity::class.java))
                } else {
                    toast("Please Enter Valid Number!")
                }
            } else {
                toast("Please Enter Mobile Number!")
            }
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
