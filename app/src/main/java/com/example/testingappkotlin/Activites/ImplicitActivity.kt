package com.example.testingappkotlin.Activites

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testingappkotlin.databinding.ActivityImplicitBinding


class ImplicitActivity : AppCompatActivity() {

    lateinit var name: String
    private lateinit var age: String
    private val whatsappNumber: String = "+923311470266"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityImplicitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        name = i.getStringExtra("name").toString()
        binding.txtName.text = name

        // Send user message Via Email

        binding.btnEmail.setOnClickListener(){

            if (binding.edtMessage.text?.isEmpty() == true){
                Toast.makeText(this, "Please enter message", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "message/html"
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("fahadhassan467@gmail.com") )
                intent.putExtra(Intent.EXTRA_SUBJECT, "Welcome!!")
                intent.putExtra(Intent.EXTRA_TEXT, "Name: $name\n"+"Your Message: ${binding.edtMessage.text.toString()}")
                try {
                    startActivity(Intent.createChooser(intent,"Please Select email"))
                }catch (e: ActivityNotFoundException){
                    Toast.makeText(this, "No Email Select", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Send user message Via Whatsapp

        binding.btnWhatsapp.setOnClickListener(){
            if (binding.edtMessage.text?.isEmpty() == true){
                Toast.makeText(this, "Please enter message", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                val intent = Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        """
                     ${"http://api.whatsapp.com/send?phone=$whatsappNumber &text=Name: $name" +"%0A"+ "Message: ${binding.edtMessage.text.toString()}"}
                     """.trimIndent()
                    )
                )
                startActivity(intent)
            }
        }

    }
}