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
import com.ayush.trulyias.login_manager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var edtEmail:EditText
    private lateinit var edtPass:EditText
    private lateinit var btnLogin:Button
    private lateinit var regTv:TextView

    private lateinit var Mauth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _init()
        FirebaseAuth.getInstance()

        val window: Window = window
        window.statusBarColor = ContextCompat.getColor(this, R.color.toolbar)

        val emailFromReg = intent.getStringExtra("email_key")
        if(emailFromReg!=null){
            edtEmail.setText(emailFromReg)
        }

        Mauth = Firebase.auth
        val loginStateManager = login_manager(applicationContext)
        if (loginStateManager.isLoggedIn) {
            startActivity(Intent(this,Sciencebook::class.java))
            return
        }


        btnLogin.setOnClickListener {
            val email  = edtEmail.text.toString()
            val passw = edtPass.text.toString()
            if (email.isBlank() || passw.isBlank()){
                Toast.makeText(this,"Please Fill All The Fields", Toast.LENGTH_SHORT).show()
            }else if (!isValidEmail(email)){
                Toast.makeText(this,"Please Enter Valid Email", Toast.LENGTH_SHORT).show()
            }else if (!isValidPassword(passw)){
                Toast.makeText(this,"Password Must be of 8 Characters", Toast.LENGTH_SHORT).show()
            }else{
                _login(email,passw)
            }
        }

    }

    private fun _login(email: String, passw: String) {
        try {
            Mauth.signInWithEmailAndPassword(email,passw)
                .addOnCompleteListener(this){
                    if (it.isSuccessful){
                        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                        val loginStateManager = login_manager(applicationContext)
                        loginStateManager.isLoggedIn = true
                        val user = FirebaseAuth.getInstance().currentUser
                        user?.let {
                            val uid = it.uid
                            val userRef = FirebaseDatabase.getInstance().getReference("users").child(uid)
                            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        val name = dataSnapshot.child("name").getValue(String::class.java)
                                        val userClass = dataSnapshot.child("class").getValue(String::class.java)
                                        val intent = Intent(this@MainActivity, Sciencebook::class.java)
                                        intent.putExtra("NAME_KEY", name)
                                        intent.putExtra("CLASS_KEY", userClass)
                                        startActivity(intent)
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                }
                            })
                        }
                        startActivity(Intent(this,Sciencebook::class.java))
                    }else{
                        Toast.makeText(applicationContext, "Error -${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }catch (e:Exception){
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }


    }

    private fun _init() {
        edtEmail = findViewById(R.id.edtEmail)
        edtPass = findViewById(R.id.edtPass)
        btnLogin = findViewById(R.id.btnLogin)
        regTv = findViewById(R.id.regTv)
        regTv.setOnClickListener {
            startActivity(Intent(this,Register::class.java))
        }
    }


    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}