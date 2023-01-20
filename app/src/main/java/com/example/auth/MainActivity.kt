package com.example.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.auth.databinding.ActivityMainBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            binding.progressbar.isVisible
            binding.login.isInvisible

            if (binding.phoneNumber.text.toString().isEmpty()) {
                binding.phoneNumber.error = "Must give number!"
//                Toast.makeText(this,"Must give number!", Toast.LENGTH_SHORT).show()
            } else if (binding.phoneNumber.text.length != 10) {
                binding.phoneNumber.error = "Must write 10 digits number"
            } else {

                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("phonenumber",binding.phoneNumber.text.toString().trim())
                startActivity(intent)
            }
        }
    }


}