package com.librosreviewproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.librosreviewproyecto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        binding.btRegister.setOnClickListener { haceRegistro() }
        binding.btLogin.setOnClickListener { haceLogin() }
    }

    private fun haceRegistro() {
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()


        auth.createUserWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this) { task ->
                var user: FirebaseUser? =null
                if (task.isSuccessful) {
                    Log.d("Autenticando","usuario creado")
                    user = auth.currentUser
                } else {
                    Log.d("Autenticando","Error creando usuario")
                }
                actualiza(user)
            }
    }

    private fun haceLogin() {
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()

        auth.signInWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this) { task ->
                var user: FirebaseUser? =null
                if (task.isSuccessful) {
                    Log.d("Autenticando","usuario autenticado")
                    user = auth.currentUser
                } else {
                    Log.d("Autenticando","Error autenticando usuario")
                }
                actualiza(user)
            }
    }

    private fun actualiza(user: FirebaseUser?) {
        if (user!=null) {
            val intent =  Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        val usuario = auth.currentUser
        actualiza(usuario)
    }
}