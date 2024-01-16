package com.example.testingappkotlin.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.adapters.RecyclerViewTaskAdapter
import com.example.testingappkotlin.modals.RecyclerViewTaskModal
import com.example.testingappkotlin.R
import java.util.Locale

class RecyclerViewTaskActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: RecyclerViewTaskAdapter
    private lateinit var list: ArrayList<RecyclerViewTaskModal>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_task)

        list = arrayListOf()
        list.add(RecyclerViewTaskModal("Fahad","qwertyuioplk","12:00 pm",true, arrayListOf(R.drawable.img)))
        list.add(RecyclerViewTaskModal("Ali","qwertyuioplk","12:00 pm",false,null))
        list.add(RecyclerViewTaskModal("Hassan","qwertyuioplk","12:00 pm",true, arrayListOf(
            R.drawable.img,
            R.drawable.img,
            R.drawable.img
        )))
        list.add(RecyclerViewTaskModal("Umer","qwertyuioplk","12:00 pm",false, arrayListOf(
            R.drawable.img,
            R.drawable.img
        )))
        list.add(RecyclerViewTaskModal("Hamza","qwertyuioplk","12:00 pm",false, arrayListOf(
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img
        )))
        list.add(RecyclerViewTaskModal("Bilal","qwertyuioplk","12:00 pm",true, arrayListOf(
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img,
            R.drawable.img
        )))

        recyclerView = findViewById(R.id.taskRecyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewTaskAdapter(this,list)
        recyclerView.adapter = adapter

        //searchView

        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

    }

    private fun filterList(query: String?) {

        if (query != null){
            val filteredData = ArrayList<RecyclerViewTaskModal>()
            for (i in list){
                if (i.name.contains(query) || i.name.lowercase(Locale.ROOT).contains(query) || i.name.uppercase(Locale.ROOT).contains(query)){
                    filteredData.add(i)
                }
            }
            adapter.setFilteredList(filteredData)
        }

    }
}