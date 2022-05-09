package com.ieszv.deintyad.backendtoni

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //PARA PASAR PARAMETROS ENTRE ACTIVIDADES SE UTILIZA BUNDLE
        val bundle = intent.extras
        var user :String? = bundle!!.getString("email", "Default")
        var  tv : TextView = findViewById(R.id.tv_Wel)
        tv.setText("Welcome$user")
        //COGIENDO IDENTIFIER DEL BOTON LOGOUT
        var  logout : Button = findViewById(R.id.bt_logout)
        logout.setOnClickListener {
            signOut()
        }
    }
    private fun signOut() {
            Firebase.auth.signOut()
            navegacionLogout()


    }
    fun navegacionLogout() {
        //CREAMOS EL INTENT PARA PASAR ENTRE ACTIVIDADES
        intent = Intent(this,LoginActivity::class.java)
        //COMENZAMOS LA ACTIVIDAD
        startActivity(intent)
        finish()
    }

}