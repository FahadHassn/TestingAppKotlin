package com.example.testingappkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testingappkotlin.databinding.ActivityFirebaseRegisterBinding
import com.example.testingappkotlin.models.UserLoginModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


class FirebaseRegisterActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFirebaseRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = Firebase.database.reference

        binding.firebaseRegisterButton.setOnClickListener {

            val name = binding.firebaseRegisterName.text.toString().trim()
            val email = binding.firebaseRegisterEmail.text.toString().trim()
            val phone = binding.firebaseRegisterPhone.text.toString().trim()
            val password = binding.firebaseRegisterPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()){
                Toast.makeText(this@FirebaseRegisterActivity, "Fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                binding.registerProgress.visibility = View.VISIBLE
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener {
                        val id: String? = it.user?.uid
                        val loginModal = UserLoginModel(name, email, phone, password, id,"null")
                        if (id != null) {
                            databaseReference.child("Users").child(id).setValue(loginModal).addOnSuccessListener {
                                binding.registerProgress.visibility = View.GONE
                                Toast.makeText(
                                    this@FirebaseRegisterActivity,
                                    "Register Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this,DrawerNavigationActivity::class.java)
                                intent.putExtra("name",name)
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(intent)
                                finish()
                            }.addOnFailureListener {
                                binding.registerProgress.visibility = View.GONE
                                Toast.makeText(this@FirebaseRegisterActivity,
                                    "User data not stored in database",
                                    Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }  .addOnFailureListener {
                        binding.registerProgress.visibility = View.GONE
                        Toast.makeText(this@FirebaseRegisterActivity,
                            "Registration Failed",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
            }

        }

        binding.firebaseRegisterLoginText.setOnClickListener {
            startActivity(Intent(this,FirebaseLoginActivity::class.java))
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,FirebaseLoginActivity::class.java))
        finish()
    }
}