package com.ieszv.deintyad.backendtoni

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    //Manejo de las llamadas las inicializamos
    private lateinit var callbackManager: CallbackManager
    //Inicializamos el boton de tipo Facebook
    private lateinit var buttonFacebookLogin: LoginButton

    companion object {
        private const val TAG = "FacebookLogin"
    }

    /**
     * Metodo OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //AUTORIZAMOS A FIREBASE
        auth = Firebase.auth
        //Clase initiliaze para la llamadad de componentes
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
                        var  loginEmail: TextInputEditText = findViewById(R.id.etLoginUsername)
                        val  email = loginEmail.text.toString()
                        //PASAMOS AL LA FUNCION SI EL EMAIL ES VALIDO PARA CHECKEARLO
                        val obj = pyObj.callAttr("isEMAILValid",email)

                        var  loginPassword : TextInputEditText = findViewById(R.id.etLoginPassword)
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
        // region Facebook Login button
            //Creamos la llamada
            callbackManager = CallbackManager.Factory.create()
            //Cogemos el boton de nuestra activity mediante su identificador
            buttonFacebookLogin = findViewById(R.id.login_button)
            //Seteamos los permisos de lectura y escritura
            buttonFacebookLogin.setReadPermissions("email", "public_profile")
            //Registramos la llamada al hilo
            buttonFacebookLogin.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    //En caso de exito cogemos el token de acceso y lo enviamos para el login con facebook
                    handleFacebookAccessToken(loginResult.accessToken)
                }
                override fun onCancel() {
                    //Caso de cancelacion se queda donde esta
                }
                override fun onError(error: FacebookException) {
                    //En caso de Error se envia el mensaje de error
                    Log.d(TAG, "facebook:onError", error)
                }
            })
        // endregion



        //endregion
    }
    /**
     * ·······
     * Intent a la actividad principal donde te envia el mensaje de bienvenida
     * ·······
     *
     */
    fun navegacionExitosa(email: String) {
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
                    navegacionExitosa(email)
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
    /**
     * ·······
     * METODO PARA QUE CUANDO EMPIECE LA APP SI ESTAS REGISTRADO LLAME AL METODO ACTUALIZAR USERINTERFACE
     * ·······
     *
     */
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    /**
     * ·······
     * METODO ON ACTIVITY RESULT
     * ·······
     *
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
    /**
     * ·······
     * METODO PARA EN EL CASO DE QUE EL TOKEN ES REGISTRADO CORRECTAMENTE ....
     * ·······
     *
     */
    private fun handleFacebookAccessToken(token: AccessToken) {
        //COGEMOS EN UNA VARIABLE LA CREDENCIAL DE INICIO DE SESION
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                //EN CASO DE QUE DESDE FIREBASE SE ACCEDA A FACEBOOK CON EXITO
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    //PASAMOS EL USUARIO
                    updateUI(user)
                } else {
                    //MENSAJE DE ERROR
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
    /**
     * ·······
     * METODO PARA ACTUALIZAR DE INTERFAZ DE USUARIO
     * ·······
     *
     */
    private fun updateUI(user: FirebaseUser?) {
        if(user != null){
            user.displayName?.let { navegacionExitosa(it) }
        }
    }



}