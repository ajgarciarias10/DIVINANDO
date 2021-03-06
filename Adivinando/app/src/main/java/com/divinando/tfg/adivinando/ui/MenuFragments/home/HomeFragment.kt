package com.divinando.tfg.adivinando.ui.MenuFragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.divinando.tfg.adivinando.databinding.FragmentHomeBinding
import com.divinando.tfg.adivinando.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    //Firebase
    private val db = FirebaseFirestore.getInstance()
    private  lateinit var  mFirebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFirebaseAuth = Firebase.auth
        getUser()

    }

    //Obtiene la puntuacion en cada juego del usuario
    private fun getUser(){

        binding.tvDivHome.visibility = View.GONE
        binding.tvDivTilHome.visibility = View.GONE
        binding.tvEncHome.visibility = View.GONE
        binding.tvPaisHome.visibility = View.GONE
        binding.tvEscudoHome.visibility = View.GONE
        binding.tvFamhome.visibility = View.GONE

        //Si el usuario no se logeo solo se ve un texto
        if (mFirebaseAuth.currentUser == null) {
            binding.noestalogueado.visibility = View.VISIBLE
            binding.noestalogueado.text = "Loguese si desea jugar"
            binding.tvDivHome.visibility = View.GONE
            binding.tvDivTilHome.visibility = View.GONE
            binding.tvEncHome.visibility = View.GONE
            binding.tvPaisHome.visibility = View.GONE
            binding.tvEscudoHome.visibility = View.GONE
            binding.tvFamhome.visibility = View.GONE

            binding.cardDivHome.visibility = View.GONE
            binding.cardDivTilHome.visibility = View.GONE
            binding.cardEncHome.visibility = View.GONE
            binding.cardPaisHome.visibility = View.GONE
            binding.cardEscudoHome.visibility = View.GONE
            binding.cardFamHome.visibility = View.GONE

        //Si el usuario se logeo muestra la informacion cargada
        }else{
            binding.tvDivHome.visibility = View.VISIBLE
            binding.tvDivTilHome.visibility = View.VISIBLE
            binding.tvEncHome.visibility = View.VISIBLE
            binding.tvPaisHome.visibility = View.VISIBLE
            binding.tvEscudoHome.visibility = View.VISIBLE
            binding.tvFamhome.visibility = View.VISIBLE
            binding.noestalogueado.visibility = View.GONE


            binding.cardDivHome.visibility = View.VISIBLE
            binding.cardDivTilHome.visibility = View.VISIBLE
            binding.cardEncHome.visibility = View.VISIBLE
            binding.cardPaisHome.visibility = View.VISIBLE
            binding.cardEscudoHome.visibility = View.VISIBLE
            binding.cardFamHome.visibility = View.VISIBLE


            //Carga los puntos de cada juego del usuario
            db.collection("Ranking").document(mFirebaseAuth.currentUser!!.email.toString()).get().addOnSuccessListener { document ->
                binding.tvDivHome.text = document.getString("divinando").toString() + " ptos."
                binding.tvDivTilHome.text = document.getString("divtildes").toString() + " ptos."
                binding.tvEncHome.text = document.getString("encadenados").toString() + " ptos."
                binding.tvPaisHome.text = document.getString("paises").toString() + " ptos."
                binding.tvEscudoHome.text = document.getString("famosos").toString() + " ptos."
                binding.tvFamhome.text = document.getString("escudos").toString() + " ptos."
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}