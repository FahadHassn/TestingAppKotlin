package com.example.testingappkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.testingappkotlin.R
import com.example.testingappkotlin.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class HomeActivity : AppCompatActivity() {

    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
            gsc = GoogleSignIn.getClient(this@HomeActivity,gso)

            //get and get email and name
            val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this@HomeActivity)
            if (googleSignInAccount != null){
                val name = googleSignInAccount.displayName
                val email = googleSignInAccount.email

                homeName.text = name
                homeEmail.text = email
            }

            //logout
            homeLogout.setOnClickListener {
                gsc.signOut().addOnSuccessListener {
                    startActivity(Intent(this@HomeActivity,LoginWithPhoneActivity::class.java))
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(
                        this@HomeActivity,
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

    }
}