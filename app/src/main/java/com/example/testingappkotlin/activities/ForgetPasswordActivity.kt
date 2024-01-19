package com.example.testingappkotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.testingappkotlin.R
import com.example.testingappkotlin.databinding.ActivityForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.apply {
            firebaseForgotButton.setOnClickListener {

                val email = firebaseForgotPasswordEmail.text.toString().trim()
                if (email.isEmpty()){
                    Toast.makeText(
                        this@ForgetPasswordActivity,
                        "Enter registered email",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener {
                        Toast.makeText(
                            this@ForgetPasswordActivity,
                            "Check your email please",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.addOnFailureListener {
                        Toast.makeText(
                            this@ForgetPasswordActivity,
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}