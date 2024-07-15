package com.example.ui.application.app.app_components.base

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ui.MainActivity
import com.example.ui.R
import com.example.ui.databinding.ActivityMainBinding
import com.example.ui.databinding.ActivityDrDetailsBinding
import com.example.ui.databinding.ActivitySignInBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging
import android.content.SharedPreferences

class SignUp: AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnSignUp.setOnClickListener {
            val name = binding.etname.text.toString()
            val address = binding.etdetail.text.toString()
            val phoneNum = binding.etdetail1.text.toString().filter { it.isDigit() }

            if (name.isNotEmpty() && address.isNotEmpty() && phoneNum.isNotEmpty()) {
                if (name.all { it.isLetter() || it.isWhitespace() } &&
                    address.all { it.isLetter() || it.isWhitespace() || it.isDigit() } &&
                    phoneNum.matches(Regex("0[0-9]{9}"))) {
                    // input is valid
                    val sharedPreferences: SharedPreferences = getSharedPreferences("HI-Card", MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putBoolean("isSignedUp", true)
                    editor.putString("name", name)
                    editor.putString("address", address)
                    editor.putString("phoneNum", phoneNum)
                    editor.apply()


                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(
                    this,
                    "Invalid input! Please check your name, address, and phone number.",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }
}