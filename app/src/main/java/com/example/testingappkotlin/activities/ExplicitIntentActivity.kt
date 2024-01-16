package com.example.testingappkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testingappkotlin.databinding.ActivityExplicitIntentBinding

class ExplicitIntentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityExplicitIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener(){
            if (binding.edtName.text?.isEmpty() == true){
                Toast.makeText(this,"Please enter name",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (binding.edtAge.text?.isEmpty() == true){
                Toast.makeText(this,"Please enter age",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                val intent = Intent(this, ImplicitActivity::class.java)
                intent.putExtra("name",binding.edtName.text.toString())
                startActivity(intent)
                binding.edtName.setText("")
                binding.edtAge.setText("")
            }
        }

    }
}