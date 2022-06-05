package com.divinando.tfg.adivinando.ui.MenuFragments.Register

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.divinando.tfg.adivinando.R
import com.divinando.tfg.adivinando.databinding.FragmentRegisterBinding
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class Register : Fragment() {
    //Inicializamos variable de autenticacion de Firebase
    private lateinit var auth : FirebaseAuth

    private val db = FirebaseFirestore.getInstance()

    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    /**
     * Metodo OnCreate
     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }
    /**
     * Aqui Inicializamos Las Variables con las que vamos a trabajar
     */
    fun initialize(){



        //region AUTORIZAMOS A FIREBASE
        auth = Firebase.auth
        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(requireContext()))
        }
        //endregion
        //region Como obtener las funciones de Python

        //COGEMOS LA INSTANCIA DE PYTHON
        val py: Python = Python.getInstance()
        //BUSCAMOS EL OBJETO QUE CONTIENE LOS METODOS A UTILIZAR
        val pyObj: PyObject = py.getModule("ValidatingEmailAndPassword")
        //endregion
        //endregion

        //region REGISTER ONCLICK EVENTO
        binding.btRegister.setOnClickListener {
            val username = binding.etRegisterUsername.text.toString()
            //Sacamos el email del campo de texto
            val  email =  binding.etRegisterEmail.text.toString()
            //PASAMOS AL LA FUNCION SI EL EMAIL ES VALIDO PARA CHECKEARLO
            val obj = pyObj.callAttr("isEMAILValid",email)
            //Sacamos la contraseña del campo de texto
            val  password =  binding.etRegisterPass.text.toString()
            //PASAMOS AL LA FUNCION SI LA CONTRASIÑA  ES VALIDA PARA CHECKEARLA
            val ob2 = pyObj.callAttr("isPasswordValid",password)
            //SACAMOS LA REPETICION DE LA CONTRASEÑA
            val  repeatPassword = binding.etRegisterPassRep.text.toString()
            // region FUNCION QUE CHECKEA LOS CAMPOS INPUTEDIT EN PYTHON
            //SI AMBAS FUNCIONES DEVUELVEN TRUE
            if(obj.toBoolean() && ob2.toBoolean()) {
                //SI LA CONTRASEÑA SE REPITE
                if(repeatPassword == password){
                    //REGISTRAMOS EL USUARIO
                    registerUser(username,email,password)
                }
                else{
                    binding.etRegisterPassRep.error = "Error no es similiar a la contraseña de arriba"
                }
            }

            else if(!obj.toBoolean()){
                //En caso de que la funcion  del checkeo de email devuelva falso
                binding.etRegisterEmail.error = "Error introduzca el email correctamente"
            }
            if (!ob2.toBoolean()){
                //En caso de que la funcion  del checkeo de contraseña devuelva falso
                binding.etRegisterPass.error = "La contraseña elegida no es segura: debe contener letras minúsculas, mayusculas, números alfanumericos y  al menos 1 carácter no alfanumérico @/&etc..."
            }

            //endregion



        }

        //endregion

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private  fun registraUsuarioEnElRanking(username : String, email : String){
            db.collection("Ranking").document(email).get().addOnSuccessListener { document ->
                if(document.data == null){
                    //region Insertamos en la tabla de ranking el usuario y devolvemos
                    val data = hashMapOf(
                        "nombre" to  username,
                        "mail" to email,
                        "divinando" to "0",
                        "divtildes" to "0",
                        "encadenados" to "0",
                        "paises" to "0",
                        "escudos" to "0",
                        "famosos" to "0"
                    )
                    db.collection("Ranking").document(email).set(data, SetOptions.merge())
                    //endregion

                }
                else{
                    Log.v("CACA","ESTA YA METIDO" )
                }
            }


    }
    /**
     * METODO PARA REGISTRAR A UN USUARIO EN FIREBASE
     */
    fun  registerUser(username:String, email: String ,password : String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                //Si la tarea de registro es exitosa
                if (task.isSuccessful) {
                    registraUsuarioEnElRanking(username, email)
                    findNavController().navigate(R.id.action_register_to_login)
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

}