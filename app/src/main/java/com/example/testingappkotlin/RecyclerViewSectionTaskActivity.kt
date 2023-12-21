package com.example.testingappkotlin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.Adapters.RecyclerSectionAdapter
import com.example.testingappkotlin.Interface.OnItemClickListener
import com.example.testingappkotlin.Modals.Section
import com.example.testingappkotlin.Modals.SectionModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RecyclerViewSectionTaskActivity : AppCompatActivity() , OnItemClickListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var sectionAdapter: RecyclerSectionAdapter
    private var list = mutableListOf<SectionModel>()
    private var sections = mutableListOf<SectionModel>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_section_task)

        //Sort
        val sections = sortedByFirstLetter(list)

        //Recyclerview
        recyclerView = findViewById(R.id.sectionRecyclerview)
        sectionAdapter = RecyclerSectionAdapter(this,sections,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = sectionAdapter

        //Button Add
        val btnAdd: Button = findViewById(R.id.buttonAdd)
        btnAdd.setOnClickListener(){
            showCustomDialog()
        }

        val txtName = findViewById<TextView>(R.id.txtSortByName)
        txtName.setOnClickListener(){
            sectionAdapter = RecyclerSectionAdapter(this,sortedByFirstLetter(list),this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = sectionAdapter

        }
        val txtDate = findViewById<TextView>(R.id.txtSortByDate)
        txtDate.setOnClickListener(){
            sectionAdapter = RecyclerSectionAdapter(this, sortedByDate(list),this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = sectionAdapter
        }
    }

    private fun showCustomDialog() {

        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_box, null)

        val editTextName = dialogView.findViewById<EditText>(R.id.customDialogName)
        val editTextDate = dialogView.findViewById<TextView>(R.id.customDialogDate)
        val btnSave = dialogView.findViewById<Button>(R.id.customDialogBtnSave)
        val btnCancel = dialogView.findViewById<Button>(R.id.customDialogBtnCancel)

        val calendar = Calendar.getInstance()

        editTextDate.setOnClickListener {
            showDatePicker(calendar,editTextDate)
        }

        builder.setView(dialogView)
        builder.setTitle("Add New Data")
        builder.setCancelable(false)

        val alertDialog = builder.create()

        btnSave.setOnClickListener {

            val name = editTextName.text.toString()
            val date = editTextDate.text.toString()

            if (name.isEmpty()){
                Toast.makeText(this,"Please enter name",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (date.isEmpty()){
                Toast.makeText(this,"Please select date",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                list.add(SectionModel(name, date))
                sectionAdapter.reloadData(sortedByFirstLetter(list))

                alertDialog.dismiss()
            }
        }

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun showDatePicker(calendar: Calendar, editTextDate: TextView) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, pickedYear, pickedMonth, pickedDay ->
                calendar.set(Calendar.YEAR, pickedYear)
                calendar.set(Calendar.MONTH, pickedMonth)
                calendar.set(Calendar.DAY_OF_MONTH, pickedDay)

                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = simpleDateFormat.format(calendar.time)

                editTextDate.text = formattedDate
            },
            year, month, day
        )

        datePickerDialog.show()
    }

//    override fun onItemClick(position: Int) {
//        val checkItem = list[position]
//        println("Position: ${list[position]}")
//        showEditDialog(checkItem,position)
//    }

    override fun onItemClick(sectionIndex: Int, itemIndexInSection: Int) {
        showEditDialog(sectionIndex,itemIndexInSection)
    }

    private fun showEditDialog(sectionIndex: Int, itemIndexInSection: Int) {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_box, null)

        val editTextName = dialogView.findViewById<EditText>(R.id.customDialogName)
        val editTextDate = dialogView.findViewById<TextView>(R.id.customDialogDate)
        val btnSave = dialogView.findViewById<Button>(R.id.customDialogBtnSave)
        val btnCancel = dialogView.findViewById<Button>(R.id.customDialogBtnCancel)

        val calendar = Calendar.getInstance()

        editTextDate.setOnClickListener {
            showDatePicker(calendar,editTextDate)
        }

        builder.setView(dialogView)
        builder.setTitle("Add New Data")
        builder.setCancelable(false)

        val alertDialog = builder.create()

        val item = list[itemIndexInSection]
//        val section = sectionAdapter.getSections()[sectionIndex]
//        val item = section.sections[itemIndexInSection]

        editTextName.setText(item.name)
        editTextDate.text = item.date

        btnSave.setOnClickListener {
            val name = editTextName.text.toString()
            val date = editTextDate.text.toString()

            if (name.isEmpty()){
                Toast.makeText(this,"Please enter name",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (date.isEmpty()){
                Toast.makeText(this,"Please select date",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
//                list.add(checkItem)
                item.name = name
                item.date = date
                sectionAdapter.reloadData(sortedByFirstLetter(list))

//                section.sections[itemIndexInSection].name = name
//                section.sections[itemIndexInSection].date = date
//                sectionAdapter.reloadData(sortedByFirstLetter(list))

                alertDialog.dismiss()
            }

        }

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }


}
    private fun sortedByFirstLetter(list: List<SectionModel>): List<Section> {
        val group = list.groupBy { it.name[0].toUpperCase() }
        return group.map { (letter, list) -> Section(letter.toString(), list) }.sortedBy { it.title }
    }

    private fun sortedByDate(list: List<SectionModel>): List<Section> {
            val group = list.groupBy { it.date.toUpperCase() }
            return group.map { (letter, list) -> Section(letter, list) }.sortedBy { it.title }
    }