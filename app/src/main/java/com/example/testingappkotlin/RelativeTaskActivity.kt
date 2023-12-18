package com.example.testingappkotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.regex.Matcher
import java.util.regex.Pattern


class RelativeTaskActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var txtEmail: TextView
    private lateinit var txtPassword: TextView
    private lateinit var button: Button
    private var passwordVisible = false

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relative_task)

        edtEmail = findViewById(R.id._edtEmailR)
        edtPassword = findViewById(R.id._edtPasswordR)
        txtEmail = findViewById(R.id._txtValidEmailR)
        txtPassword = findViewById(R.id._txtValidPasswordR)
        button = findViewById(R.id._btnSignInR)
        val defaultTextColor = edtEmail.currentTextColor

        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isValidEmail(edtEmail.text.toString().trim())) {
                    txtEmail.text = "Perfect!"
                    txtEmail.setTextColor(Color.GREEN)
                    if (isValidPassword(edtPassword.text.toString().trim())) {
                        enableTrue()
                    }
                } else {
                    enableFalse()
                    txtEmail.text = "Invalid email!"
                    txtEmail.setTextColor(Color.RED)
                }

                if (edtEmail.text.toString().isEmpty()) {
                    enableFalse()
                    txtEmail.text = "Email should be valid"
                    txtEmail.setTextColor(defaultTextColor)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isValidPassword(edtPassword.text.toString().trim())) {
                    txtPassword.text = "Perfect!"
                    txtPassword.setTextColor(Color.GREEN)
                    if (isValidEmail(edtEmail.text.toString().trim())) {
                        enableTrue()
                    }
                } else {
                    enableFalse()
                    txtPassword.text = "Invalid password!"
                    txtPassword.setTextColor(Color.RED)
                }

                if (edtPassword.text.toString().isEmpty()) {
                    enableFalse()
                    txtPassword.text = "Password should be valid"
                    txtPassword.setTextColor(defaultTextColor)

                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        //hide and show password

        edtPassword.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableRight = 2 // Index of drawableEnd
                if (event.rawX >= edtPassword.right - edtPassword.compoundDrawables[drawableRight].bounds.width()
                ) {
                    togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }

        button.setOnClickListener {
//            if (edtEmail.text.toString().isNotEmpty()){
//                if (!isValidEmail(edtEmail.text.toString().trim())) {
//                    Toast.makeText(this,"Invalid email address",Toast.LENGTH_SHORT).show()
//                    return@setOnClickListener
//                }
//            }else{
//                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//            if (edtPassword.text.toString().isNotEmpty()){
//                if (!isValidPassword(edtPassword.text.toString().trim())) {
//                    Toast.makeText(this, "Password mush have minimum 8 letters\nUppercase and Lowercase letters\n Special symbols and Digits also", Toast.LENGTH_SHORT).show()
//                    return@setOnClickListener
//                }
//            }else{
//                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))

        }

    }

    private fun togglePasswordVisibility() {
        if (passwordVisible) {
            // Hide the password
            edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            edtPassword.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.baseline_visibility_24,
                0
            )
            passwordVisible = false
        } else {
            // Show the password
            edtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            edtPassword.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.baseline_visibility_off_24,
                0
            )
            passwordVisible = true
        }

        // Move the cursor to the end of the text
        edtPassword.setSelection(edtPassword.text.length)
    }

    private fun isValidPassword(password: String?): Boolean {
        password?.let {
            val passwordPattern =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_+=])(?=\\S+$).{8,}$"
            val passwordMatcher = Regex(passwordPattern)

            return passwordMatcher.find(password) != null
        } ?: return false
    }

    private fun isValidEmail(email: String?): Boolean {
        val emailPattern =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern: Pattern = Pattern.compile(emailPattern)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun enableTrue(){
        button.isEnabled = true
        button.backgroundTintList = ContextCompat.getColorStateList(this@RelativeTaskActivity, R.color.black)
        button.setTextColor(ContextCompat.getColor(this@RelativeTaskActivity, R.color.white))
    }

    private fun enableFalse(){
        button.isEnabled = false
        button.backgroundTintList = ContextCompat.getColorStateList(this@RelativeTaskActivity, R.color.buttonColor)
        button.setTextColor(ContextCompat.getColor(this@RelativeTaskActivity, R.color.buttonTextColor))
    }

}