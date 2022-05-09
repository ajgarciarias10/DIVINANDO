package com.ieszv.deintyad.backendtoni

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    /**
     * Metodo OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //AUTORIZAMOS A FIREBASE
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initialize()
    }
        /**
         * Aqui Inicializamos Las Variables con las que vamos a trabajar
         */
        private fun initialize() {
            //region Como obtener las funciones de Python
                if (! Python.isStarted()) {
                    Python.start(AndroidPlatform(this))
                }
                //COGEMOS LA INSTANCIA DE PYTHON
                val py: Python = Python.getInstance()
                //BUSCAMOS EL OBJETO QUE CONTIENE LOS METODOS A UTILIZAR
                val pyObj: PyObject = py.getModule("ValidatingEmailAndPassword")
            //endregion
            //region CAMPOS DE TIPO INPUT EDIT TEXT
                val rEmail: TextInputEditText = findViewById(R.id.inputEmail)
                val rPassword : TextInputEditText = findViewById(R.id.inputContraseña)
                val rRPasword : TextInputEditText = findViewById(R.id.inputRPContraseña)
            //endregion
            //region  CAMPOS DE TIPO BUTTON
             val registerButton : Button = findViewById(R.id.bt_registrar)
             val enviameALogin : Button = findViewById(R.id.bt_tencuenta)
            //endregion
            //region REGISTER ONCLICK EVENTO
                registerButton.setOnClickListener {
                    //Sacamos el email del campo de texto
                    val  email = rEmail.text.toString()
                    //PASAMOS AL LA FUNCION SI EL EMAIL ES VALIDO PARA CHECKEARLO
                    val obj = pyObj.callAttr("isEMAILValid",email)
                    //Sacamos la contraseña del campo de texto
                    val  password = rPassword.text.toString()
                    //PASAMOS AL LA FUNCION SI LA CONTRASIÑA  ES VALIDA PARA CHECKEARLA
                    val ob2 = pyObj.callAttr("isPasswordValid",password)
                    //SACAMOS LA REPETICION DE LA CONTRASEÑA
                    val  repeatPassword = rRPasword.text.toString()
                    // region FUNCION QUE CHECKEA LOS CAMPOS INPUTEDIT EN PYTHON
                        //SI AMBAS FUNCIONES DEVUELVEN TRUE
                        if(obj.toBoolean() && ob2.toBoolean()) {
                            //SI LA CONTRASEÑA SE REPITE
                            if(repeatPassword == password){
                                //REGISTRAMOS EL USUARIO
                                registerUser(email,password)
                            }
                            else{
                                rRPasword.error = "Error no es similiar a la contraseña de arriba"
                            }
                        }

                        else if(!obj.toBoolean()){
                            //En caso de que la funcion  del checkeo de email devuelva falso
                            rEmail.error = "Error introduzca el email correctamente"
                        }
                         if (!ob2.toBoolean()){
                             //En caso de que la funcion  del checkeo de contraseña devuelva falso
                             rPassword.error = "La contraseña elegida no es segura: debe contener letras minúsculas, mayusculas, números alfanumericos y  al menos 1 carácter no alfanumérico @/&etc..."
                        }

                    //endregion



                }

            //endregion
            //region EVENTOONCLICK PARA MOVER AL LOGIN
                enviameALogin.setOnClickListener(View.OnClickListener {
                    //CAMBIAMOS ENTRE ACTIVIDAD
                    startActivity(Intent(this,LoginActivity::class.java))
                    //FINALIZAMOS
                    finish()

                })
        }
        /**
         * METODO PARA REGISTRAR A UN USUARIO EN FIREBASE
         */
        fun  registerUser(email: String ,password : String){
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) {
                            task ->
                        //Si la tarea de registro es exitosa
                        if (task.isSuccessful){
                            //CAMBIAMOS ENTRE ACTIVIDAD
                            startActivity(Intent(this,LoginActivity::class.java))
                            //FINALIZAMOS
                            finish()
                        }
                        else{
                            try {
                                throw task.exception!!
                            } catch (e: FirebaseAuthWeakPasswordException) {
                                e.localizedMessage?.let { Log.e(TAG, it) }
                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                e.localizedMessage?.let { Log.e(TAG, it) }
                            } catch (e: FirebaseAuthUserCollisionException) {
                                e.localizedMessage?.let { Log.e(TAG, it) }
                            } catch (e: Exception) {
                                e.localizedMessage?.let { Log.e(TAG, it) }
                            }
                        }
                    }
            }
}