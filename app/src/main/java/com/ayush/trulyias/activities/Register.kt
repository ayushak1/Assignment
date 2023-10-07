package com.ayush.trulyias.activities

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ayush.trulyias.R
import com.ayush.trulyias.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Register : AppCompatActivity() {
    private lateinit var edtName:EditText
    private lateinit var edtEmail:EditText
    private lateinit var edtPass:EditText
    private lateinit var edtClass:EditText
    private lateinit var regBtn:Button
    private lateinit var loginTv:TextView

    private lateinit var Mauth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        _init()
        val window: Window = window
        window.statusBarColor = ContextCompat.getColor(this, R.color.toolbar)

        FirebaseAuth.getInstance()

        Mauth = Firebase.auth

        loginTv.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        regBtn.setOnClickListener {
            val email  = edtEmail.text.toString()
            val passw = edtPass.text.toString()
            val name = edtName.text.toString()
            val _class = edtClass.text.toString()
            if (email.isBlank() || passw.isBlank() || name.isBlank() || _class.isBlank()){
                Toast.makeText(this,"Please Fill All The Fields", Toast.LENGTH_SHORT).show()
            }else if (!isValidEmail(email)){
                Toast.makeText(this,"Please Enter Valid Email", Toast.LENGTH_SHORT).show()
            }else if (!isValidPassword(passw)){
                Toast.makeText(this,"Password Must be of 8 Characters", Toast.LENGTH_SHORT).show()
            }else if (_class.toInt() !in 6..10) {
                Toast.makeText(this,"Class should be between 6 and 10", Toast.LENGTH_SHORT).show()
            }else{
                _signup(email,passw,name,_class)
            }
        }

    }

    private fun _signup(email: String, passw: String, name: String, _class: String) {

        val user = User(
            name = name,
            email =email,
            Class = _class
        )
        try {
            Mauth.createUserWithEmailAndPassword(email,passw)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful){
                        Toast.makeText(this,"Sign Up Successfully",Toast.LENGTH_SHORT).show()
                        addUserToDb(user)
                        val intent = Intent(this@Register, MainActivity::class.java)
                        intent.putExtra("email_key", email)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,"Error- ${it.exception?.message}",Toast.LENGTH_SHORT).show()
                    }
                }
        }catch (e:Exception){
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }

    }
    private fun addUserToDb(user: User) {
        Firebase.database.getReference("users").child(Mauth.uid.toString()).setValue(user)
    }

    private fun _init() {
        edtName = findViewById(R.id.edtName)
        edtEmail = findViewById(R.id.edtEmail)
        edtPass = findViewById(R.id.edtPass)
        edtClass = findViewById(R.id.edtClass)
        regBtn = findViewById(R.id.regBtn)
        loginTv = findViewById(R.id.loginTV)
    }


    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }



}