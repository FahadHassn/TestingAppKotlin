package com.example.testingappkotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testingappkotlin.databinding.ActivityOtpactivityBinding

class OTPActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val otpCode = intent.getStringExtra("opt")

        binding.apply {
            otpEditText.setText(otpCode)
        }

    }
}