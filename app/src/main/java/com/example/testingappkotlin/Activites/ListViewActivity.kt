package com.example.testingappkotlin.Activites

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.testingappkotlin.Adapters.ListViewAdapter
import com.example.testingappkotlin.Modals.ItemViewModal
import com.example.testingappkotlin.R

class ListViewActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        val listViewModal = mutableListOf<ItemViewModal>()
        listViewModal.add(ItemViewModal("Pakistan","Country name is pakistan"))
        listViewModal.add(ItemViewModal("India","Country name is india"))
        listViewModal.add(ItemViewModal("China","Country name is china"))
        listViewModal.add(ItemViewModal("America","Country name is america"))
        listViewModal.add(ItemViewModal("Russia","Country name is russia"))

        listView = findViewById(R.id.list_view)
        listView.adapter = ListViewAdapter(this,listViewModal)

    }
}