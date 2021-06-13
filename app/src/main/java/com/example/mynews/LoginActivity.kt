package com.example.mynews

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mynews.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * This is a login activity. It goes through the firebase authentication
 * If the user is not logged in, it sets a new theme
 * otherwise authentication process goes behind the splash screen
 *
 * If Log in or Sign up is successful, it redirects us to the main activity,
 * where we can scroll through the news
 */
class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth?.currentUser

        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            setTheme(R.style.Theme_MyNews)
            setContentView(binding.root)
        }
        init()
    }

    private fun init() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            login(email, password)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            register(email, password)
        }
    }

    private fun login(email: String, password: String) {
        //Sign existing user in
        mAuth!!.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show()
                    //Sign in was successful
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    d("SIGN IN: ", "SIGN IN SUCCESSFUL")
                } else {
                    Toast.makeText(this, "Could not sign in", Toast.LENGTH_SHORT).show()
                    //Sign in was not successful
                    d("SIGN IN: ", "SIGN IN NOT SUCCESSFUL")
                }
            }
    }

    private fun register(email: String, password: String) {
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { it ->
                if (it.isSuccessful) {
                    Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show()
                    val user: FirebaseUser = mAuth!!.currentUser!!
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    d("USER: ", user.email.toString())
                } else {
                    Toast.makeText(this, "Could not register", Toast.LENGTH_SHORT).show()
                    d("ERROR: ", it.toString())
                }
            }
    }
}