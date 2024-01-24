package com.example.testingappkotlin.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.R
import com.example.testingappkotlin.adapters.RecyclerSectionAdapter
import com.example.testingappkotlin.interfaces.OnItemClickListener
import com.example.testingappkotlin.models.Section
import com.example.testingappkotlin.models.SectionModel
import com.example.testingappkotlin.models.UserLoginModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RecyclerViewSectionTaskActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sectionAdapter: RecyclerSectionAdapter
    private var list = mutableListOf<SectionModel>()
    private lateinit var progressBar: ProgressBar
    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_section_task)

        databaseReference = Firebase.database.reference

        //progress
        progressBar = findViewById(R.id.sectionRecyclerviewProgress)

        //Recyclerview
        recyclerView = findViewById(R.id.sectionRecyclerview)
        sectionAdapter = RecyclerSectionAdapter(this, sortedByFirstLetter(list), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = sectionAdapter

        //Button Add
        val btnAdd: Button = findViewById(R.id.buttonAdd)
        btnAdd.setOnClickListener() {
            showAddDialog()
        }

        //readData from database
        readData()

        //Sort by name
        val txtName = findViewById<TextView>(R.id.txtSortByName)
        txtName.setOnClickListener() {
            sectionAdapter.reloadData(sortedByFirstLetter(list))

        }

        //Sort by date
        val txtDate = findViewById<TextView>(R.id.txtSortByDate)
        txtDate.setOnClickListener() {
            sectionAdapter.reloadData(sortedByDate(list))
        }
    }

    private fun readData() {
        databaseReference.child("Section Recyclerview").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    list.clear()
                    for (dataSnapshot in snapshot.children) {
                        val sectionModel = dataSnapshot.getValue(SectionModel::class.java)
                        progressBar.visibility = View.GONE
                        sectionModel?.let { list.add(it) }
                        sectionAdapter.reloadData(sortedByFirstLetter(list))
                    }
                } else {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@RecyclerViewSectionTaskActivity,
                        "Data not available",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
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
                val id = databaseReference.push().key
                val sectionModel = SectionModel(name.trim(), date.trim(),lastName.trim(),id)
                if (id != null) {
                    databaseReference.child("Section Recyclerview").child(id).setValue(sectionModel).addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Add Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        sectionAdapter.reloadData(sortedByFirstLetter(list))
                    }.addOnFailureListener {
                        Toast.makeText(this,
                            "Failed!",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                }else{
                    Toast.makeText(this,
                        "Something went wrong",
                        Toast.LENGTH_SHORT)
                        .show()
                }
                alertDialog.dismiss()

//                list.add(SectionModel(name.trim(), date.trim(),lastName.trim()))
//                sectionAdapter.reloadData(sortedByFirstLetter(list))
//                alertDialog.dismiss()
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

                val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                val formattedDate = simpleDateFormat.format(calendar.time)

                editTextDate.text = formattedDate
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    @SuppressLint("MissingInflatedId")
    override fun onEditClicked(sectionModel: SectionModel, position: Int) {
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

        editTextName.setText(sectionModel.name)
        editTextLastName.setText(sectionModel.lastName)
        editTextDate.text = sectionModel.date

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
                val updatedData = mapOf(
                    "name" to newName,
                    "lastName" to newLastName,
                    "date" to newDate
                )
                databaseReference.child("Section Recyclerview")
                    .child(sectionModel.id!!)
                    .updateChildren(updatedData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
                    }
                alertDialog.dismiss()

//                sectionModel.name = newName
//                sectionModel.date = newDate
//                sectionModel.lastName = newLastName
//                sectionAdapter.reloadData(sortedByFirstLetter(list))
//
//                alertDialog.dismiss()
            }
        }

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    override fun onDeleteClicked(sectionModel: SectionModel) {
//        list.remove(sectionModel)
        sectionModel.id?.let {
            databaseReference.child("Section Recyclerview").child(it)
                .removeValue().also {
                list.remove(sectionModel)
            }
        }
        sectionAdapter.reloadData(sortedByFirstLetter(list))
    }

    private fun sortedByFirstLetter(list: List<SectionModel>): List<Section> {
        val group = list.groupBy { it.name[0].uppercaseChar() }
        return group.map { (letter, list) -> Section(letter.toString(), list) }
            .sortedBy { it.title }
    }

    private fun sortedByDate(list: List<SectionModel>): List<Section> {
        val group = list.groupBy { it.date.uppercase(Locale.getDefault()) }
        return group.map { (letter, list) -> Section(letter, list) }.sortedBy { it.title }
    }
}