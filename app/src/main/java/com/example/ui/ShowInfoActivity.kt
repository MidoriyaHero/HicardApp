package com.example.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ui.databinding.ActivityDrDetailsBinding

class ShowInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data: HashMap<String, String>? =
            intent.getSerializableExtra("data") as HashMap<String, String>?

        data?.let {
            val patientName = it["patient_name"]
            val patientAge = it["patient_age"]
            val patientPhone = it["patient_phone"]
            val patientHistory = it["patient_history"]
            val longitude = it["long"]
            val latitude = it["lat"]

            // Set the data to the views
            binding.txtDrmarcus.text = patientName
            binding.txtChardiologist.text = ("Age: $patientAge")
            binding.txtChardiologist1.text = ("Phone: $patientPhone")
            binding.historymedical.text = patientHistory
            binding.RowchatIcon.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:$patientPhone"))
                startActivity(intent)
            }
            binding.button2.setOnClickListener {
                val gmmIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
    }
}
