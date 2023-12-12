package com.example.testingappkotlin

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.testingappkotlin.Adapters.ListViewAdapter
import com.example.testingappkotlin.Modals.ListViewModal

class ListViewActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        val listViewModal = mutableListOf<ListViewModal>()
        listViewModal.add(ListViewModal("Pakistan","Country name is pakistan"))
        listViewModal.add(ListViewModal("India","Country name is india"))
        listViewModal.add(ListViewModal("China","Country name is china"))
        listViewModal.add(ListViewModal("America","Country name is america"))
        listViewModal.add(ListViewModal("Russia","Country name is russia"))

        listView = findViewById(R.id.list_view)
        listView.adapter = ListViewAdapter(this,listViewModal)

    }
}