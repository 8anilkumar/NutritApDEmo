package com.anil.nutritapdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.anil.nutritapdemo.databinding.ActivityMainBinding
import com.anil.nutritapdemo.databinding.ActivityOtpactivityBinding

class OTPActivity : AppCompatActivity() {

    companion object {
        const val otp = "1234"
    }

    private lateinit var binding: ActivityOtpactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()

    }

    private fun initListener() {
        binding.btnOTP.setOnClickListener {
            val nOtp = binding.edOTP.text.trim().toString()
            if (nOtp.isNotEmpty()) {
                if(nOtp == otp) {
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    toast("OTP Not Matched!")
                }
            } else {
                toast("Please Enter OTP!")
            }
        }
    }

    private  fun toast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}