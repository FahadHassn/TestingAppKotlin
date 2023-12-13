package com.example.testingappkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.Adapters.RecyclerviewAdapter
import com.example.testingappkotlin.Modals.ItemViewModal

class RecyclerviewActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var list: ArrayList<ItemViewModal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        list = arrayListOf()
        list.add(ItemViewModal("Pakistan","Country name is pakistan"))
        list.add(ItemViewModal("India","Country name is india"))
        list.add(ItemViewModal("China","Country name is china"))
        list.add(ItemViewModal("Russia","Country name is russia"))
        list.add(ItemViewModal("America","Country name is america"))

        recyclerView = findViewById(R.id._recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerviewAdapter(this,list)

    }
}