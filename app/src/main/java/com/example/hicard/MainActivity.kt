package com.example.hicard

import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textview: TextView  = findViewById(R.id.textView1)

        FirebaseMessaging.getInstance().subscribeToTopic("web_app")
            .addOnCompleteListener { task ->
                var msg = "Done"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }
                println(msg)
            }

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result != null && !TextUtils.isEmpty(task.result)) {
                        val token: String = task.result!!
                        textview.text = token;
                        println("Token: $token")
                        val tokenDevice = hashMapOf(
                            "device" to token
                        )
                        Firebase.firestore.collection("Token")
                            .document("Token-$token")
                            .set(tokenDevice, SetOptions.merge())
                        println("Added document")
                    }
                }
            }
    }
}


