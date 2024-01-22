package com.example.testingappkotlin.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.adapters.RecyclerviewAdapter
import com.example.testingappkotlin.models.ItemViewModel
import com.example.testingappkotlin.R

class RecyclerviewActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var list: ArrayList<ItemViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        list = arrayListOf()
        list.add(ItemViewModel("Pakistan","Country name is pakistan"))
        list.add(ItemViewModel("India","Country name is india"))
        list.add(ItemViewModel("China","Country name is china"))
        list.add(ItemViewModel("Russia","Country name is russia"))
        list.add(ItemViewModel("America","Country name is america"))

        recyclerView = findViewById(R.id._recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerviewAdapter(this,list)

    }
}