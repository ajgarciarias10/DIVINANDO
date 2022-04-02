package com.ieszv.deintyad.backendtoni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialize();
    }


    /**
     * ·······
     * Aqui Inicializamos Las Variables con las que vamos a trabajar
     * ·······
     *
     */
    private fun initialize() {
        //region CAMPOS DE TIPO INPUT EDIT TEXT
        var  loginEmail: TextInputEditText = findViewById(R.id.inputEmail)
        var  loginPassword : TextInputEditText = findViewById(R.id.inputContraseña)
        //endregion
        //region  CAMPOS DE TIPO BUTTON
        var  loginButton : Button = findViewById(R.id.bt_login)
                loginButton.setOnClickListener {

                }
        var  loginGoRegisterButton : Button = findViewById(R.id.bt_register)
                loginGoRegisterButton.setOnClickListener {

                }
        //endregion

    }
}