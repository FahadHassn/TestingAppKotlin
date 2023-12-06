package com.example.testingappkotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id._text)
        textView.text = "Create"
        Toast.makeText(this,"Create",Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        textView.text = "Start"
        Toast.makeText(this,"Start",Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        textView.text = "Resume"
        Toast.makeText(this,"Resume",Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        textView.text = "Pause"
        Toast.makeText(this,"Pause",Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        textView.text = "Stop"
        Toast.makeText(this,"Stop",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        textView.text = "Destroy"
        Toast.makeText(this,"Destroy",Toast.LENGTH_SHORT).show()
    }
}