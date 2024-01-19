package com.example.testingappkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testingappkotlin.databinding.ActivityFirebaseRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class FirebaseRegisterActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFirebaseRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.firebaseRegisterButton.setOnClickListener {

            val name = binding.firebaseRegisterName.text.toString().trim()
            val email = binding.firebaseRegisterEmail.text.toString().trim()
            val password = binding.firebaseRegisterPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this@FirebaseRegisterActivity, "Enter credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this@FirebaseRegisterActivity,
                            "Register Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }  .addOnFailureListener {
                        Toast.makeText(this@FirebaseRegisterActivity,
                            "Registration Failed",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        }

        binding.firebaseRegisterLoginText.setOnClickListener {
            startActivity(Intent(this,FirebaseLoginActivity::class.java))
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,FirebaseLoginActivity::class.java))
        finish()
    }
}