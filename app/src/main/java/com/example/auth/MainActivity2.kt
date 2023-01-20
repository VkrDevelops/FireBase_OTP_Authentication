package com.example.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.auth.databinding.ActivityMain2Binding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var verificationuser: String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var credentials:PhoneAuthCredential


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()
        myOTPSent()
// Force reCAPTCHA flow
        FirebaseAuth.getInstance().firebaseAuthSettings.forceRecaptchaFlowForTesting(true)

        binding.verify.setOnClickListener {

            if (binding.otp.text.toString().isEmpty()) {
                binding.otp.error = "Enter OTP"
//                Toast.makeText(this,"Must give number!", Toast.LENGTH_SHORT).show()
            } else if (binding.otp.text.length != 6) {
                binding.otp.error = "Must write 6 digits number"
            } else {


                val Otp=binding.otp.text.toString().trim()

                credentials=PhoneAuthProvider.getCredential(verificationuser,Otp)
                signInWithPhoneAuthCredential(credentials)
            }
        }

    }

    private fun myOTPSent() {

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                verificationuser=verificationId.trim()

            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@MainActivity2,e.message,Toast.LENGTH_LONG).show()
                Log.d("usama","${e.message}")
            }
        }
        val phone=intent.getStringExtra("phonenumber").toString().trim()
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber("+92$phone")                      // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS)        // Timeout and unit
            .setActivity(this@MainActivity2)                 // Activity (for callback binding)
            .setCallbacks(callbacks)                        // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   val intent=Intent(this,Dashboard::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    Toast.makeText(this@MainActivity2,"${task.exception}",Toast.LENGTH_SHORT).show()
                    Log.d("waqar","${task.exception}")
                }
            }
    }

}