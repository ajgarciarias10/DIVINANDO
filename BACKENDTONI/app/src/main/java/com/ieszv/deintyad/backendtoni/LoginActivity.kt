package com.ieszv.deintyad.backendtoni

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    /**
     * Metodo OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //AUTORIZAMOS A FIREBASE
        auth = Firebase.auth

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
        //region Como obtener las funciones de Python
            if (! Python.isStarted()) {
                Python.start(AndroidPlatform(this))
            }
            //COGEMOS LA INSTANCIA DE PYTHON
            val py: Python = Python.getInstance()
            //BUSCAMOS EL OBJETO QUE CONTIENE LOS METODOS A UTILIZAR
            val pyObj: PyObject = py.getModule("ValidatingEmailAndPassword")
        //endregion
        //region  CAMPOS DE TIPO BUTTON
            var  loginButton : Button = findViewById(R.id.btLogin)
                    loginButton.setOnClickListener {
                        // region FUNCION QUE CHECKEA LOS CAMPOS INPUTEDIT EN PYTHON
                            //region CAMPOS DE TIPO INPUT EDIT TEXT
                        var  loginEmail: TextInputEditText = findViewById(R.id.inputEmail)
                        val  email = loginEmail.text.toString()
                        //PASAMOS AL LA FUNCION SI EL EMAIL ES VALIDO PARA CHECKEARLO
                        val obj = pyObj.callAttr("isEMAILValid",email)

                        var  loginPassword : TextInputEditText = findViewById(R.id.inputContraseña)
                        val  password = loginPassword.text.toString()
                        //PASAMOS AL LA FUNCION SI LA CONTRASIÑA  ES VALIDA PARA CHECKEARLA
                        val ob2 = pyObj.callAttr("isPasswordValid",password)
                        //endregion
                            //SI AMBAS FUNCIONES DEVUELVEN TRUE
                            if(obj.toBoolean() && ob2.toBoolean()) {
                                    loginUser(email,password)


                            }

                            else if(!obj.toBoolean()){
                                //En caso de que la funcion  del checkeo de email devuelva falso
                                loginEmail.error = "Error introduzca el email correctamente"
                            }
                            if (!ob2.toBoolean()){
                                //En caso de que la funcion  del checkeo de contraseña devuelva falso
                                loginPassword.error = "La contraseña elegida no es segura: debe contener letras minúsculas, mayusculas, números alfanumericos y  al menos 1 carácter no alfanumérico @/&etc..."
                            }

                        //endregion




                    }
            var  loginGoRegisterButton : Button = findViewById(R.id.btCreateAccount)
                    loginGoRegisterButton.setOnClickListener {
                        startActivity(Intent(this,RegisterActivity::class.java))
                        finish()
                    }



        //endregion
    }

    /**
     * ·······
     *  METODO PARA LOGUEAR A UN USUARIO EN FIREBASE
     * ·······
     *
     */
        fun  loginUser(email: String ,password : String){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) {
                    task ->
                //SI EL LOGUEO ES EXITOSO
                if (task.isSuccessful){
                    //PARA PASAR PARAMETROS ENTRE ACTIVIDADES SE UTILIZA BUNDLE
                    val bundle = Bundle()
                    //CREAMOS EL INTENT PARA PASAR ENTRE ACTIVIDADES
                    intent = Intent(this,MainActivity::class.java)
                    //COMO VAMOS A ENVIAR CADENAS HACEMOS PUTSTRING
                    bundle.putString("email",email)
                    //PASAMOS DENTRO DEL INTENT
                    intent.putExtras(bundle)
                    //COMENZAMOS LA ACTIVIDAD
                    startActivity(intent)
                    finish()
                }
                else{
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        e.localizedMessage?.let { Log.e(ContentValues.TAG, it) }
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        e.localizedMessage?.let { Log.e(ContentValues.TAG, it) }
                    } catch (e: FirebaseAuthUserCollisionException) {
                        e.localizedMessage?.let { Log.e(ContentValues.TAG, it) }
                    } catch (e: Exception) {
                        e.localizedMessage?.let { Log.e(ContentValues.TAG, it) }
                    }
                }
            }
        }
    //endregion

}