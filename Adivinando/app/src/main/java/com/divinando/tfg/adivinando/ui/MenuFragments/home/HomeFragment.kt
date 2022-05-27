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



    private fun getUser(){
       

        binding.tvDivHome.visibility = View.GONE
        binding.tvDivTilHome.visibility = View.GONE
        binding.tvEncHome.visibility = View.GONE
        binding.tvPaisHome.visibility = View.GONE
        binding.tvEscudoHome.visibility = View.GONE
        binding.tvFamhome.visibility = View.GONE


        if (mFirebaseAuth.currentUser == null) {
            binding.noestalogueado.visibility = View.VISIBLE
            binding.noestalogueado.text = "Loguese si desea jugar"
            binding.tvDivHome.visibility = View.GONE
            binding.tvDivTilHome.visibility = View.GONE
            binding.tvEncHome.visibility = View.GONE
            binding.tvPaisHome.visibility = View.GONE
            binding.tvEscudoHome.visibility = View.GONE
            binding.tvFamhome.visibility = View.GONE

        }else{
            binding.tvDivHome.visibility = View.VISIBLE
            binding.tvDivTilHome.visibility = View.VISIBLE
            binding.tvEncHome.visibility = View.VISIBLE
            binding.tvPaisHome.visibility = View.VISIBLE
            binding.tvEscudoHome.visibility = View.VISIBLE
            binding.tvFamhome.visibility = View.VISIBLE
            binding.noestalogueado.visibility = View.GONE



            db.collection("Ranking").document(mFirebaseAuth.currentUser!!.email.toString()).get().addOnSuccessListener { document ->
                binding.tvDivHome.text = document.getString("divinando").toString()
                binding.tvDivTilHome.text = document.getString("divtildes").toString()
                binding.tvEncHome.text = document.getString("encadenados").toString()
                binding.tvPaisHome.text = document.getString("paises").toString()
                binding.tvEscudoHome.text = document.getString("escudos").toString()
                binding.tvFamhome.text = document.getString("famosos").toString()
            }
        }





    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}