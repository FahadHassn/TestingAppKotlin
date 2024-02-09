package com.example.testingappkotlin.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testingappkotlin.classes.Encoder
import com.example.testingappkotlin.databinding.ActivityEncryptionBinding


class EncryptionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEncryptionBinding
    private lateinit var clipboardManager: ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEncryptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    }

    fun encryption(view: View) {
        // get text from edittext
        val temp: String = binding.encryptionTextField.text.toString()

        // pass the string to the encryption
        // algorithm and get the encrypted code
        val rv: String = Encoder.encode(temp)

        // set the code to the edit text
        binding.encryptedText.text = rv
    }

    fun copy(view: View) {
        // get the string from the textview and trim all spaces
        val data: String = binding.encryptedText.text.toString().trim()

        // check if the textview is not empty
        if (data.isNotEmpty()) {

            // copy the text in the clip board
            val temp = ClipData.newPlainText("text", data)
            clipboardManager.setPrimaryClip(temp)

            // display message that the text has been copied
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
        }
    }

}