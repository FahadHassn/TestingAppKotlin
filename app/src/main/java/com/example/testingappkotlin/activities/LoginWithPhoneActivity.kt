package com.example.testingappkotlin.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.testingappkotlin.R
import com.example.testingappkotlin.databinding.ActivityLoginWithPhoneBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginWithPhoneActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginWithPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        gsc=GoogleSignIn.getClient(this,gso)
        firebaseAuth = FirebaseAuth.getInstance()

        //session maintain
        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (googleSignInAccount != null){
            homeActivity()
        }

        binding.apply {

            //login with phone
            firebaseLoginPhoneButton.setOnClickListener {
                val phone = "+92" + firebaseLoginPhone.text?.trim().toString()
                if (phone.isEmpty()){
                    Toast.makeText(
                        this@LoginWithPhoneActivity,
                        "Enter phone number",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    otpSend(phone)
                }
            }

            //login with google
            googleButton.setOnClickListener {
                gsc.signOut()
                val signInClient = gsc.signInIntent
                launcher.launch(signInClient)
            }
        }
    }

    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if (result.resultCode== Activity.RESULT_OK ){
            val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful){
                val account:GoogleSignInAccount=task.result

                val credential=GoogleAuthProvider.getCredential(account.idToken,null)
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful){
                        homeActivity()
                        Toast.makeText(this,"Successfully",Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(this,"Failed!!",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }else{
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
        }
    }

    private fun otpSend(phone: String) {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(
                    this@LoginWithPhoneActivity,
                    "Verification Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {

                val intent = Intent(this@LoginWithPhoneActivity, OTPActivity::class.java)
                intent.putExtra("opt",verificationId)
                startActivity(intent)
                Toast.makeText(
                    this@LoginWithPhoneActivity,
                    "Code Sent",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    private fun homeActivity() {
        startActivity(Intent(this,HomeActivity::class.java))
        finish()
    }
}