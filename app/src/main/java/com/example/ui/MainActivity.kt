package com.example.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtname: TextView = findViewById(R.id.name)
        val txtadd: TextView = findViewById(R.id.address)
        val txtphone: TextView = findViewById(R.id.phone)
        val button: Button = findViewById(R.id.button)
        val sharedPreferences: SharedPreferences = getSharedPreferences("HI-Card", MODE_PRIVATE)

        val name = sharedPreferences.getString("name", "Default Name")
        val address = sharedPreferences.getString("address", "Default Address")
        val phone = sharedPreferences.getString("phoneNum", "Default Phone")
        button.setOnClickListener {
            val fcmSharedPreferences = getSharedPreferences("FCM_DATA", MODE_PRIVATE)
            val data = HashMap<String, String>()
            fcmSharedPreferences.all.forEach { (key, value) ->
                if (value is String) {
                    data[key] = value
                }
            }

            val intentInfo = Intent(this, ShowInfoActivity::class.java)
            if (data.isNotEmpty()) {
                intentInfo.putExtra("data", data)
            }
            startActivity(intentInfo)
        }

        txtname.text = name
        txtadd.text = address
        txtphone.text = phone
        FirebaseMessaging.getInstance().subscribeToTopic("web_app")
            .addOnCompleteListener { task ->
                var msg = "Done"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }
            }
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result != null && !TextUtils.isEmpty(task.result)) {
                        val token: String = task.result!!
                        println("Token: $token")
                        val tokenDevice = hashMapOf(
                            "device" to token,
                            "name" to name,
                            "address" to address,
                            "phone" to phone
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
