package com.example.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ui.application.app.app_components.base.SignUp

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the intent has FCM data
        val data: HashMap<String, String>? = intent.getSerializableExtra("data") as HashMap<String, String>?

        if (data != null) {
            // If there's FCM data, start ShowInfoActivity with the data
            val intentInfo = Intent(this, ShowInfoActivity::class.java)
            intentInfo.putExtra("data", data)
            startActivity(Intent(this, MainActivity::class.java))
            startActivity(intentInfo)
        } else {
            // Get SharedPreferences
            val sharedPreferences = getSharedPreferences("HI-Card", MODE_PRIVATE)
            val isSignedUp = sharedPreferences.getBoolean("isSignedUp", false)

            // Check if the user is signed up
            if (isSignedUp) {
                // User is signed up, start MainActivity
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // User is not signed up, start SignUp activity
                startActivity(Intent(this, SignUp::class.java))
            }
        }
        // Close LauncherActivity
        finish()
    }
}