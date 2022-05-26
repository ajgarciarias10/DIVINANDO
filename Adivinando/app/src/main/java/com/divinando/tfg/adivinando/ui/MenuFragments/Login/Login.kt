package com.divinando.tfg.adivinando.ui.MenuFragments.Login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.divinando.tfg.adivinando.R
import com.divinando.tfg.adivinando.databinding.FragmentLoginBinding
import com.divinando.tfg.adivinando.ui.MainActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : Fragment() {

    //Inicializamos variable de Autenticacion de Firebase
    private lateinit var auth: FirebaseAuth

    //Manejo de las llamadas las inicializamos
    private lateinit var callbackManager: CallbackManager

    //Inicializamos Variable tipo registro de cliente google
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val TAG = "FacebookLogin"
        private const val RC_SIGN_IN = 9001
    }
    //Variable para hacer el findByViewID
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    /**
     * Metodo  en el momento de crear la vista
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root


    }

    /**
     * Metodo al crearse la vista
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //AUTORIZAMOS A FIREBASE
        auth = Firebase.auth
        //region Como obtener las funciones de Python
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(requireContext()))
        }
        //COGEMOS LA INSTANCIA DE PYTHON
        val py: Python = Python.getInstance()
        //BUSCAMOS EL OBJETO QUE CONTIENE LOS METODOS A UTILIZAR
        val pyObj: PyObject = py.getModule("ValidatingEmailAndPassword")
        //endregion
        //region  CAMPOS DE TIPO BUTTON
        binding.btLogin.setOnClickListener {
            // region FUNCION QUE CHECKEA LOS CAMPOS INPUTEDIT EN PYTHON
            //region CAMPOS DE TIPO INPUT EDIT TEXT
            val email = binding.etLoginUsername.text.toString()
            //PASAMOS AL LA FUNCION SI EL EMAIL ES VALIDO PARA CHECKEARLO
            val obj = pyObj.callAttr("isEMAILValid", email)
            val password = binding.etLoginPassword.text.toString()
            //PASAMOS AL LA FUNCION SI LA CONTRASIÑA  ES VALIDA PARA CHECKEARLA
            val ob2 = pyObj.callAttr("isPasswordValid", password)
            //endregion
            //SI AMBAS FUNCIONES DEVUELVEN TRUE
            if (obj.toBoolean() && ob2.toBoolean()) {
                loginUser(email, password)


            } else if (!obj.toBoolean()) {
                //En caso de que la funcion  del checkeo de email devuelva falso
                binding.etLoginUsername.error = "Error introduzca el email correctamente"
            }
            if (!ob2.toBoolean()) {
                //En caso de que la funcion  del checkeo de contraseña devuelva falso
                binding.etLoginPassword.error =
                    "La contraseña elegida no es segura: debe contener letras minúsculas, mayusculas, números alfanumericos y  al menos 1 carácter no alfanumérico @/&etc..."
            }

            //endregion


        }
        binding.btCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }
        // region Facebook Login button
        //Creamos la llamada
        callbackManager = CallbackManager.Factory.create()
        //Seteamos los permisos de lectura y escritura
        binding.loginButton.setReadPermissions("email", "public_profile")
        //Registramos la llamada al hilo
        binding.loginButton.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
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
        //region Login with Google
        //Variable googleSingOptions con cliente web
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        //Variable googleSignClient que coge el cliente
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        //IDENTIFICANDO EL BOTON
        //Accion del boton de login
        binding.sigNInBtn.setOnClickListener {
            signIn()
        }
        //Firebase  Auth Instance
        auth = FirebaseAuth.getInstance()

        //endregion


    }

    /**
     * ·······
     * METODO  signIn PARA Loguearse
     * ·······
     *
     */
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    /**
     * ·······
     *  METODO PARA LOGUEAR A UN USUARIO EN FIREBASE
     * ·······
     *
     */
    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                //SI EL LOGUEO ES EXITOSO
                if (task.isSuccessful) {
                    MainActivity.ObjUser.mail = email
                    findNavController().navigate(R.id.action_login_to_nav_home)
                } else {
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

    /**
     * ·······
     * METODO PARA QUE CUANDO EMPIECE LA APP SI ESTAS REGISTRADO LLAME AL METODO ACTUALIZAR USERINTERFACE
     * ·······
     *
     */
    override fun onStart() {
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
        //Hilo de Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data)
        //Hilo de google
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
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
            .addOnCompleteListener(requireActivity()) { task ->
                //EN CASO DE QUE DESDE FIREBASE SE ACCEDA A FACEBOOK CON EXITO
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    //PASAMOS EL USUARIO
                    updateUI(user)
                } else {
                    //MENSAJE DE ERROR
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    /**
     * ·······
     * METODO  firebaseAuthWithGoogle PARA AUTENTICACION DE FIREBASE CON GOOGLE
     * ·······
     *
     */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser

                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
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
        if (user != null) {
            user.displayName?.let { findNavController().navigate(R.id.action_login_to_nav_home) }
            MainActivity.ObjUser.mail = user.email.toString()
        }
    }
}