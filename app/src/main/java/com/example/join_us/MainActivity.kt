package com.example.join_us

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.join_us.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.alreadyLoginTextview.setOnClickListener {
            startActivity(Intent(this, LogIn::class.java))
            finish()
        }

        binding.createAccountButton.setOnClickListener {
            val email = binding.emailEdittext.text.toString()
            val userName = binding.usernameEdittext.text.toString()
            val password = binding.passwordEdittext.text.toString()
            val re_password = binding.confirmPasswordEdittext.text.toString()

            if(email.isEmpty() || userName.isEmpty() || password.isEmpty() || re_password.isEmpty())
            {
                Toast.makeText(this , "Please fill the required Details" , Toast.LENGTH_SHORT).show()
            }
            else if (password != re_password)
            {
                Toast.makeText(this , "Kindly confirm your password correctly" , Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(this) {task ->
                    if(task.isSuccessful)
                    {
                        Toast.makeText(this , "Successfully created new account" , Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this , LogIn::class.java))
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this , "Registration failed : ${task.exception?.message}" , Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}