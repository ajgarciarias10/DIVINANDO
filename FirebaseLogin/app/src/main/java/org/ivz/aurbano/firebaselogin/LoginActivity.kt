package org.ivz.aurbano.firebaselogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var etLoginUsername: EditText
    private lateinit var etLoginPassword: EditText
    private lateinit var btLogin: Button
    private lateinit var btForgotPass: Button
    private lateinit var btCreateAccount: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        etLoginUsername = findViewById(R.id.etLoginUsername)
        etLoginPassword = findViewById(R.id.etLoginPassword)
        btLogin = findViewById(R.id.btLogin)
        btForgotPass = findViewById(R.id.btForgotPass)
        btCreateAccount = findViewById(R.id.btCreateAccount)

        btLogin.setOnClickListener {
            val email = etLoginUsername.text.toString()
            val password = etLoginPassword.text.toString()

            if(checkEmpty(email, password)) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
            } else {

            }
        }

        btForgotPass.setOnClickListener {

        }

        btCreateAccount.setOnClickListener {

        }
    }

    private fun checkEmpty(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }
}