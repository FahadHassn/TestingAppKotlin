package com.example.testingappkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testingappkotlin.R
import com.example.testingappkotlin.databinding.ActivityCryptographyBinding

class CryptographyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCryptographyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            cryptographyEncryptionButton.setOnClickListener {
                startActivity(Intent(this@CryptographyActivity,EncryptionActivity::class.java))
            }

            cryptographyDecryptionButton.setOnClickListener {
                startActivity(Intent(this@CryptographyActivity,DecryptionActivity::class.java))
            }

        }

    }
}