package com.example.testingappkotlin.activities

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.testingappkotlin.adapters.ListViewAdapter
import com.example.testingappkotlin.models.ItemViewModel
import com.example.testingappkotlin.R

class ListViewActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        val listViewModal = mutableListOf<ItemViewModel>()
        listViewModal.add(ItemViewModel("Pakistan","Country name is pakistan"))
        listViewModal.add(ItemViewModel("India","Country name is india"))
        listViewModal.add(ItemViewModel("China","Country name is china"))
        listViewModal.add(ItemViewModel("America","Country name is america"))
        listViewModal.add(ItemViewModel("Russia","Country name is russia"))

        listView = findViewById(R.id.list_view)
        listView.adapter = ListViewAdapter(this,listViewModal)

    }
}