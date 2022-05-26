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
import com.google.firebase.firestore.FirebaseFirestore

class EndFragment : Fragment() {

    private var _binding: FragmentEndBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()


    //User data
    var userID = ""
    var userName = ""
    var userMail = ""
    var userDivinando = ""
    var userDivtildes = ""
    var userEncadenados = ""
    var userPaises = ""
    var userEscudos = ""
    var userFamosos = ""
    var userPoints = ""
    //game data
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

    private fun init(){

        game = MainActivity.ObjUser.game

        points = MainActivity.ObjUser.point
                db.collection("Usuarios").document(MainActivity.ObjUser.id).get().addOnSuccessListener {

                        userID = it.get("id").toString()
                        userName = it.get("nombre").toString()
                        userMail = it.get("mail").toString()
                        userDivinando = it.get("divinando").toString()
                        userDivtildes = it.get("divtildes").toString()
                        userEncadenados = it.get("encadenados").toString()
                        userPaises = it.get("paises").toString()
                        userEscudos = it.get("escudos").toString()
                        userFamosos = it.get("famosos").toString()
                        binding.tvPlayerRecord.text = game + " " + it.get(game).toString()
                        binding.tvPlayerPoint.text = game + " " + points
                        save(game)

                    db.collection("Usuarios").document(MainActivity.ObjUser.id).set(
                        hashMapOf(
                            "id" to userID,
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