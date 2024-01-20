package com.example.testingappkotlin.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.testingappkotlin.R
import com.example.testingappkotlin.databinding.ActivityFirebaseLoginBinding
import com.google.firebase.auth.FirebaseAuth

class FirebaseLoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFirebaseLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.apply {

            //Login
            firebaseLoginButton.setOnClickListener {

                val email = binding.firebaseLoginEmail.text.toString().trim()
                val password = binding.firebaseLoginPassword.text.toString().trim()

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(
                        this@FirebaseLoginActivity,
                        "Enter credentials",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }else {
                    firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@FirebaseLoginActivity,
                                "Login Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            firebaseLoginEmail.setText("")
                            firebaseLoginPassword.setText("")
                        }.addOnFailureListener {
                            Toast.makeText(
                                this@FirebaseLoginActivity,
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }

            //start register activity
            firebaseLoginCreateAccount.setOnClickListener {
                startActivity(Intent(this@FirebaseLoginActivity,FirebaseRegisterActivity::class.java))
                finish()
            }

            //forget password
            firebaseLoginForgetPassword.setOnClickListener {
                startActivity(Intent(this@FirebaseLoginActivity,ForgetPasswordActivity::class.java))
            }

            //login with phone
            firebaseLoginWithNumber.setOnClickListener {
                startActivity(Intent(this@FirebaseLoginActivity,LoginWithPhoneActivity::class.java))
            }
        }
    }
}