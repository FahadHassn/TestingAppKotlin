package com.example.testingappkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.Adapters.RecyclerSectionAdapter
import com.example.testingappkotlin.Modals.Section
import com.example.testingappkotlin.Modals.SectionModel

class RecyclerViewSectionTaskActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_section_task)

        // Add list data
        var list = arrayListOf(
            SectionModel("Fahad","20/12/2023"),
            SectionModel("Ali","17/11/2022"),
            SectionModel("Ahmad","17/11/2022"),
            SectionModel("Hassan","12/04/2033"),
            SectionModel("Hamza","12/04/2033"),
            SectionModel("Umer","02/17/2000"))

        //Sort
        val sections = groupContactsByFirstLetter(list)

        //Recyclerview
        recyclerView = findViewById(R.id.sectionRecyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerSectionAdapter(this,sections)

    }

    private fun groupContactsByFirstLetter(list: List<SectionModel>): List<Section> {
        val group = list.groupBy { it.name[0].toUpperCase() }
        return group.map { (letter, list) -> Section(letter.toString(), list) }.sortedBy { it.title }
    }
}