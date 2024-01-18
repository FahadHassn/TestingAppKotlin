package com.example.testingappkotlin.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.testingappkotlin.R
import com.example.testingappkotlin.databinding.ActivitySharedPreferencesSignupBinding

class SharedPreferencesSignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySharedPreferencesSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        binding.signUpButton.setOnClickListener {

            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (name.isEmpty()){
                Toast.makeText(this,"Enter name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (email.isEmpty()){
                Toast.makeText(this,"Enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (password.isEmpty()){
                Toast.makeText(this,"Enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{

                val intent = Intent(this,BottomNavigationActivity::class.java)

                editor.apply {
                    putString("name",name)
                    putString("email",email)
                    putString("password",password)
                    putBoolean("state",true)
                    apply()
                    startActivity(intent)
                    finish()
                }
            }
        }

        binding.loginText.setOnClickListener {
            val intent = Intent(this,SharedPreferencesLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}