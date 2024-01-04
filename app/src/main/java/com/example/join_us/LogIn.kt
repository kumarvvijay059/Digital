package com.example.join_us

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.join_us.databinding.ActivityLogInBinding
import com.example.join_us.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LogIn : AppCompatActivity() {
    private val binding: ActivityLogInBinding by lazy {
        ActivityLogInBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val currentUser:FirebaseUser? = auth.currentUser
        if(currentUser != null)
        {
            Toast.makeText(this , "already logged In" , Toast.LENGTH_SHORT).show()
            startActivity(Intent(this , MainPage::class.java))
            finish()
        }
        else
        {
            Toast.makeText(this , "You need to login first" , Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.createAccountTextview.setOnClickListener {
            startActivity(Intent(this , MainActivity::class.java))
            finish()
        }

        binding.loginButton.setOnClickListener {
            val userName = binding.usernameEdittext.text.toString()
            val email = binding.emailEdittext.text.toString()
            val password = binding.passwordEdittext.text.toString()

            if (userName.isEmpty() || email.isEmpty() || password.isEmpty())
            {
                Toast.makeText(this , "Please fill all the required boxes" , Toast.LENGTH_SHORT).show()
            }
            else
            {
                auth.signInWithEmailAndPassword(email , password).addOnCompleteListener { task->
                    if(task.isSuccessful)
                    {
                        Toast.makeText(this , "Logged In Successfully" , Toast.LENGTH_SHORT).show()
                        val intent = Intent(this , MainPage::class.java)
                        intent.putExtra("Username", userName)
                        startActivity(intent)
//                        startActivity(Intent(this , MainPage::class.java))
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this , "Failed to logged In : ${task.exception?.message}" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}