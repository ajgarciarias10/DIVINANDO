package com.ieszv.deintyad.backendtoni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
        val  py = Python.getInstance()
        val pyobj = py.getModule("FunctionsforBack")
        initialize(pyobj)
    }
    /**
     * ·······
     * Aqui Inicializamos Las Variables con las que vamos a trabajar
     * ·······
     *
     */
    private fun initialize(pyobj: PyObject) {
        //region CAMPOS DE TIPO INPUT EDIT TEXT
        lateinit var  rEmail: TextInputEditText
        rEmail = findViewById(R.id.inputEmail)
        lateinit var  rPassword : TextInputEditText
        rPassword = findViewById(R.id.inputContraseña)
        lateinit var  rRPasword : TextInputEditText
        rRPasword = findViewById(R.id.inputRPContraseña)
        //endregion
        //region  CAMPOS DE TIPO BUTTON
        var  registerButton : Button = findViewById(R.id.bt_registrar)
        //region REGISTER ONCLICK EVENTO
            registerButton.setOnClickListener {
                val  email = rEmail.text.toString()
                var obj = pyobj.callAttr("isEMAILValid",email)
                val  password = rPassword.text.toString()
                var obj2 = pyobj.callAttr("isEMAILValid",email)
                val  repeatPassword = rRPasword.text.toString()
                //FUNCION QUE CHECKEA LOS CAMPOS INPUTEDIT EN PYTHON


            }
        //endregion
        var tengoCuenta : Button = findViewById(R.id.bt_tencuenta)
        tengoCuenta.setOnClickListener {

        }


        //endregion
        //region FIREBASEAUTH
            lateinit var auth : FirebaseAuth
            auth = Firebase.auth
        //endregion


    }
}