package com.example.testingappkotlin.Activites

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testingappkotlin.Adapters.SpinnerAdapter
import com.example.testingappkotlin.databinding.ActivitySpinnerTaskBinding

class SpinnerTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySpinnerTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataList = listOf("Item 1", 67, "Item 3")
        binding.customSpinner.adapter = SpinnerAdapter(this,dataList)

        binding.customSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = dataList[position]
                Toast.makeText(applicationContext,"Selected: $selectedItem",Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }
}