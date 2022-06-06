package com.divinando.tfg.adivinando.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.divinando.tfg.adivinando.R
import com.divinando.tfg.adivinando.databinding.FragmentEndBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class EndFragment : Fragment() {

    private var _binding: FragmentEndBinding? = null
    private val binding get() = _binding!!
    //Firebase
    private val db = FirebaseFirestore.getInstance()
    private  lateinit var  mFirebaseAuth: FirebaseAuth


    //datos del usuario
    var userName = ""
    var userMail = ""
    var userDivinando = ""
    var userDivtildes = ""
    var userEncadenados = ""
    var userPaises = ""
    var userEscudos = ""
    var userFamosos = ""
    var userPoints = ""
    //datos del juego
    lateinit var name: String
    lateinit var game: String
    lateinit var points:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEndBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        binding.btNextEnd.setOnClickListener {
            findNavController().navigate(R.id.end_tohome)
        }

    }
    //obtiene los datos del juego y guarda los datos del usuario en la base de datos
    private fun init(){
        mFirebaseAuth = Firebase.auth
        game = MainActivity.ObjUser.game
        points = MainActivity.ObjUser.point
                db.collection("Ranking").document(mFirebaseAuth.currentUser!!.email.toString()).get().addOnSuccessListener {
                        userName = it.get("nombre").toString()
                        userMail = it.get("mail").toString()
                        userDivinando = it.get("divinando").toString()
                        userDivtildes = it.get("divtildes").toString()
                        userEncadenados = it.get("encadenados").toString()
                        userPaises = it.get("paises").toString()
                        userEscudos = it.get("escudos").toString()
                        userFamosos = it.get("famosos").toString()
                        binding.tvPlayerRecord.text = game + " " + it.get(game).toString()
                        binding.tvPlayerPoint.text = points + " ptos."
                        save(game)

                    db.collection("Ranking").document(mFirebaseAuth.currentUser!!.email.toString()).set(
                        hashMapOf(
                            "nombre" to userName,
                            "mail" to userMail,
                            "divinando" to userDivinando,
                            "divtildes" to userDivtildes,
                            "encadenados" to userEncadenados,
                            "paises" to userPaises,
                            "escudos" to userEscudos,
                            "famosos" to userFamosos
                        )
                    )
                }


    }
    //Establece el nombre del juego y su puntuacion
    private fun save(s: String){
        if(s == "divinando"){
            userDivinando = (userDivinando.toInt() + points.toInt()).toString()
        }
        else if(s == "divtildes"){
            userDivtildes = (userDivtildes.toInt() + points.toInt()).toString()
        }
        else if(s == "encadenados"){
            userEncadenados = (userEncadenados.toInt() + points.toInt()).toString()
        }
        else if(s == "paises"){
            userPaises = (userPaises.toInt() + points.toInt()).toString()
        }
        else if(s == "escudos"){
            userEscudos = (userEscudos.toInt() + points.toInt()).toString()
        }
        else if(s == "famosos"){
            userFamosos = (userFamosos.toInt() + points.toInt()).toString()
        }
    }



}