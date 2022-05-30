package com.divinando.tfg.adivinando.ui.MenuFragments.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.divinando.tfg.adivinando.R
import com.divinando.tfg.adivinando.databinding.FragmentGalleryBinding
import com.divinando.tfg.adivinando.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private  lateinit var  mFirebaseAuth: FirebaseAuth

    var size = 0
    var divinando = "0"
    var divUser = ""
    var divTilde = "0"
    var divTilUser = ""
    var encadenados = "0"
    var encUser = ""
    var paises = "0"
    var paisUser = ""
    var escudos = "0"
    var escUser = ""
    var famosos = "0"
    var famUser = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

    }

    private fun init() {
        mFirebaseAuth = Firebase.auth
        val db = FirebaseFirestore.getInstance()
        db.collection("Ranking").get().addOnSuccessListener {
            it.documents.forEach{
                documentSnapshot ->
                getData(documentSnapshot.data!!.get("mail").toString())
                Log.v("RAKING", documentSnapshot.data!!.get("mail").toString())
            }

            //readUsers(it.documents)
        }

    }

    private fun getData(nombre:String) {

        db.collection("Ranking").document(nombre).get().addOnSuccessListener {
            if (divinando.toInt() < it.get("divinando").toString().toInt()) {
                divinando = it.get("divinando").toString()
                divUser = it.get("mail").toString().replace("@gmail.com","")
            }
            if (divTilde.toInt() < it.get("divtildes").toString().toInt()) {
                divTilde = it.get("divtildes").toString()
                divTilUser = it.get("mail").toString().replace("@gmail.com","")
            }
            if (encadenados.toInt() < it.get("encadenados").toString().toInt()) {
                encadenados = it.get("encadenados").toString()
                encUser = it.get("mail").toString().replace("@gmail.com","")
            }
            if (paises.toInt() < it.get("paises").toString().toInt()) {
                paises = it.get("paises").toString()
                paisUser = it.get("mail").toString().replace("@gmail.com","")
            }
            if (escudos.toInt() < it.get("escudos").toString().toInt()) {
                escudos = it.get("escudos").toString()
                escUser = it.get("mail").toString().replace("@gmail.com","")
            }
            if (famosos.toInt() < it.get("famosos").toString().toInt()) {
                famosos = it.get("famosos").toString()
                famUser = it.get("mail").toString().replace("@gmail.com","")
            }
            setText()
        }
    }


    private fun setText(){
        binding.tvDivRanking.setText ("$divinando ptos  $divUser")
        binding.tvGame.text = "Divinando"
        binding.tvDivTilRanking.setText("$divTilde ptos  $divTilUser")
        binding.tvGame2.text = "Tildes"
        binding.tvEncRanking.setText("$encadenados ptos  $encUser")
        binding.tvGame3.text = "4 Chooser"
        binding.tvPaisRanking.setText("$paises ptos  $paisUser")
        binding.tvGame4.text = "Guess It"
        binding.tvEscudoRanking.setText("$escudos ptos  $escUser")
        binding.tvGame5.text = "Country Guesser"
        binding.tvFamRanking.setText("$famosos ptos  $famUser")
        binding.tvGame6.text ="Encadenados "
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}