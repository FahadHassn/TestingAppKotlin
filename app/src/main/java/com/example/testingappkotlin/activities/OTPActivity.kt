package com.example.testingappkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.testingappkotlin.databinding.ActivityOtpactivityBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth

class OTPActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth
        val intent = intent
        val otpCode = intent.getStringExtra("opt")

        binding.apply {
            otpVerifyButton.setOnClickListener {
                val editTextCode = otpEditText.text.toString()
                if (otpCode != null){
                    verifyCode(otpCode,editTextCode)
                }
            }
        }

    }

    private fun verifyCode(otpCode: String, editTextCode: String) {
        val credential = PhoneAuthProvider.getCredential(otpCode,editTextCode)
        signInWithCredentials(credential)
    }

    private fun signInWithCredentials(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential).addOnSuccessListener {
            Toast.makeText(
                this,
                "Login Successfully",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            Toast.makeText(
                this,
                "Incorrect OTP",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}