package com.example.testingappkotlin.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.testingappkotlin.R
import com.example.testingappkotlin.databinding.ActivitySharedPreferencesBinding

class SharedPreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySharedPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val check = sharedPref.getBoolean("state",false)

        val intent = Intent(this,BottomNavigationActivity::class.java)

        if (check){
            startActivity(intent)
            finish()
        }else{
            binding.sharedButton.setOnClickListener {

                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()

                if (email.isEmpty()){
                    Toast.makeText(this,"Enter email",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }else if (password.isEmpty()){
                    Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }else{
                    editor.apply {
                        putString("email",email)
                        putString("password",password)
                        putBoolean("state",true)
                        apply()
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}