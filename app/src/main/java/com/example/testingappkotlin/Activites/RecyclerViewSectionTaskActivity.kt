package com.example.testingappkotlin.Activites

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.Adapters.RecyclerSectionAdapter
import com.example.testingappkotlin.Interface.OnItemClickListener
import com.example.testingappkotlin.Modals.Section
import com.example.testingappkotlin.Modals.SectionModel
import com.example.testingappkotlin.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RecyclerViewSectionTaskActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sectionAdapter: RecyclerSectionAdapter
    private var list = mutableListOf<SectionModel>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_section_task)

        //Sort
        val sections = sortedByFirstLetter(list)

        //Recyclerview
        recyclerView = findViewById(R.id.sectionRecyclerview)
        sectionAdapter = RecyclerSectionAdapter(this, sections, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = sectionAdapter

        //Button Add
        val btnAdd: Button = findViewById(R.id.buttonAdd)
        btnAdd.setOnClickListener() {
            showAddDialog()
        }


        //Sort by name
        val txtName = findViewById<TextView>(R.id.txtSortByName)
        txtName.setOnClickListener() {
            sectionAdapter = RecyclerSectionAdapter(this, sortedByFirstLetter(list), this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = sectionAdapter

        }

        //Sort by date
        val txtDate = findViewById<TextView>(R.id.txtSortByDate)
        txtDate.setOnClickListener() {
            sectionAdapter = RecyclerSectionAdapter(this, sortedByDate(list), this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = sectionAdapter
        }
    }
    @SuppressLint("MissingInflatedId")
    private fun showAddDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_box, null)

        val editTextName = dialogView.findViewById<EditText>(R.id.customDialogName)
        val editTextLastName = dialogView.findViewById<EditText>(R.id.customDialogLastName)
        val editTextDate = dialogView.findViewById<TextView>(R.id.customDialogDate)
        val btnSave = dialogView.findViewById<Button>(R.id.customDialogBtnSave)
        val btnCancel = dialogView.findViewById<Button>(R.id.customDialogBtnCancel)

        val calendar = Calendar.getInstance()

        editTextDate.setOnClickListener {
            showDatePicker(calendar, editTextDate)
        }

        builder.setView(dialogView)
        builder.setTitle("Add New Data")
        builder.setCancelable(false)

        val alertDialog = builder.create()

        btnSave.setOnClickListener {

            val name = editTextName.text.toString()
            val lastName = editTextLastName.text.toString()
            val date = editTextDate.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (date.isEmpty()) {
                Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                list.add(SectionModel(name.trim(), date.trim(),lastName.trim()))
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

    @SuppressLint("MissingInflatedId")
    override fun onEditClicked(sectionModel: SectionModel, name: String, date: String, lastName: String) {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_box, null)

        val editTextName = dialogView.findViewById<EditText>(R.id.customDialogName)
        val editTextLastName = dialogView.findViewById<EditText>(R.id.customDialogLastName)
        val editTextDate = dialogView.findViewById<TextView>(R.id.customDialogDate)
        val btnSave = dialogView.findViewById<Button>(R.id.customDialogBtnSave)
        val btnCancel = dialogView.findViewById<Button>(R.id.customDialogBtnCancel)

        val calendar = Calendar.getInstance()

        editTextDate.setOnClickListener {
            showDatePicker(calendar, editTextDate)
        }

        builder.setView(dialogView)
        builder.setTitle("Edit Data")
        builder.setCancelable(false)

        val alertDialog = builder.create()

        editTextName.setText(name)
        editTextLastName.setText(lastName)
        editTextDate.text = date

        btnSave.setOnClickListener {
            val newName = editTextName.text.toString()
            val newLastName = editTextLastName.text.toString()
            val newDate = editTextDate.text.toString()

            if (newName.isEmpty()) {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (newDate.isEmpty()) {
                Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                sectionModel.name = newName
                sectionModel.date = newDate
                sectionModel.lastName = newLastName
                sectionAdapter.reloadData(sortedByFirstLetter(list))

                alertDialog.dismiss()
            }
        }

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    override fun onDeleteClicked(sectionModel: SectionModel) {
        list.remove(sectionModel)
        sectionAdapter.reloadData(sortedByFirstLetter(list))
    }

    private fun sortedByFirstLetter(list: List<SectionModel>): List<Section> {
        val group = list.groupBy { it.name[0].toUpperCase() }
        return group.map { (letter, list) -> Section(letter.toString(), list) }
            .sortedBy { it.title }
    }

    private fun sortedByDate(list: List<SectionModel>): List<Section> {
        val group = list.groupBy { it.date.toUpperCase() }
        return group.map { (letter, list) -> Section(letter, list) }.sortedBy { it.title }
    }
}