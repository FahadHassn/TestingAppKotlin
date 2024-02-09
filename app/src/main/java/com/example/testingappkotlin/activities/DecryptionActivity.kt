package com.example.testingappkotlin.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testingappkotlin.classes.Decoder
import com.example.testingappkotlin.databinding.ActivityDecryptionBinding


class DecryptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDecryptionBinding
    private lateinit var clipboardManager: ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDecryptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    }

    fun decryption(view: View) {
        // get code from edittext
        // get code from edittext
        val temp: String = binding.decryptionTextField.text.toString()
        Log.e("dec", "text - $temp")

        // pass the string to the decryption algorithm
        // and get the decrypted text

        // pass the string to the decryption algorithm
        // and get the decrypted text
        val rv: String = Decoder.decode(temp)

        // set the text to the edit text for display

        // set the text to the edit text for display
        binding.decryptedText.text = rv
        Log.e("dec", "text - $rv")
    }
    fun copy(view: View) {
        // get the string from the textview and trim all spaces
        // get the string from the textview and trim all spaces
        val data: String = binding.decryptedText.text.toString().trim()

        // check if the textview is not empty

        // check if the textview is not empty
        if (data.isNotEmpty()) {

            // copy the text in the clip board
            val temp = ClipData.newPlainText("text", data)

            // display message that the text has been copied
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
        }
    }
}