package com.ieszv.deintyad.backendtoni

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    //lateinit var auth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
       // auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initialize()
    }
    /**
     * ·······
     * Aqui Inicializamos Las Variables con las que vamos a trabajar
     * ·······
     *
     */

    private fun initialize() {

        //region Como obtener las funciones de Python
            if (! Python.isStarted()) {
                Python.start(AndroidPlatform(this))
            }
            val py: Python = Python.getInstance()
            val pyObj: PyObject = py.getModule("ValidatingEmailAndPassword")
        //endregion
        //region CAMPOS DE TIPO INPUT EDIT TEXT
        val rEmail: TextInputEditText = findViewById(R.id.inputEmail)
        val rPassword : TextInputEditText = findViewById(R.id.inputContraseña)
        val rRPasword : TextInputEditText = findViewById(R.id.inputRPContraseña)
        //endregion
        //region  CAMPOS DE TIPO BUTTON
        val registerButton : Button = findViewById(R.id.bt_registrar)
        //region REGISTER ONCLICK EVENTO
            registerButton.setOnClickListener {
                val  email = rEmail.text.toString()
                val obj = pyObj.callAttr("isEMAILValid",email)


                val  password = rPassword.text.toString()
                val ob2 = pyObj.callAttr("isPasswordValid",password)
                val  repeatPassword = rRPasword.text.toString()

                // region FUNCION QUE CHECKEA LOS CAMPOS INPUTEDIT EN PYTHON
                    if(obj.toBoolean() && ob2.toBoolean()) {
                        rRPasword.visibility = View.VISIBLE
                        if(repeatPassword == password){
                            Toast.makeText(applicationContext, "BIENVENIDO$email", Toast.LENGTH_LONG).show()
                            //registerUser(email,password)
                        }
                        else{
                            rRPasword.error = "Error no es similiar a la contraseña de arriba"
                        }
                    }

                    else if(!obj.toBoolean()){
                        rEmail.error = "Error introduzca el email correctamente"
                    }
                     if (!ob2.toBoolean()){
                         rPassword.error = "La contraseña elegida no es segura: debe contener letras minúsculas, mayusculas, números alfanumericos y  al menos 1 carácter no alfanumérico @/&etc..."
                    }

                //endregion



            }

        //endregion
    }
    //FALTA  LOGIN FIREBASE
  /*  fun registerUser(email: String ,password : String){

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) {
                task ->
            if (task.isSuccessful){
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(applicationContext,"Register Failed",Toast.LENGTH_LONG).show()

            }
        }
    }

   */
}