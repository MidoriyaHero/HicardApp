package com.example.ui.application.app.app_components.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hicard.MainActivity
import com.example.ui.R
import com.example.ui.databinding.ActivityMainBinding
import com.example.ui.databinding.ActivityDrDetailsBinding
import com.example.ui.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp: AppCompatActivity() {
    private lateinit var binding:ActivitySignInBinding
    private lateinit var firebaseAuth:FirebaseAuth
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnSignUp.setOnClickListener{
            val name = binding.etname.text.toString()
            val address = binding.etdetail.text.toString()
            val phoneNum = binding.etdetail1.text.toString().filter { it.isDigit() }

            if (name.isNotEmpty() && address.isNotEmpty() && phoneNum.isNotEmpty()) {
                if (name.all { it.isLetter() || it.isWhitespace() } &&
                    address.all { it.isLetter() || it.isWhitespace() || it.isDigit() } &&
                    phoneNum.matches(Regex("0[0-9]{9}"))) {
                    // input is valid
                    firebaseAuth.createUserWithEmailAndPassword(name,phoneNum).addOnCompleteListener{
                        if (it.isSuccessful){
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }

                    }

                } else {
                    Toast.makeText(this, "Invalid inputPlease check your name, address, and phone number.", Toast.LENGTH_SHORT).show()

                }
            }else {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
            
        }
    }
}