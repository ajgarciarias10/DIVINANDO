package com.divinando.tfg.adivinando.ui.MenuFragments.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
                divUser = it.get("mail").toString()
            }
            if (divTilde.toInt() < it.get("divtildes").toString().toInt()) {
                divTilde = it.get("divtildes").toString()
                divTilUser = it.get("mail").toString()
            }
            if (encadenados.toInt() < it.get("encadenados").toString().toInt()) {
                encadenados = it.get("encadenados").toString()
                encUser = it.get("mail").toString()
            }
            if (paises.toInt() < it.get("paises").toString().toInt()) {
                paises = it.get("paises").toString()
                paisUser = it.get("mail").toString()
            }
            if (escudos.toInt() < it.get("escudos").toString().toInt()) {
                escudos = it.get("escudos").toString()
                escUser = it.get("mail").toString()
            }
            if (famosos.toInt() < it.get("famosos").toString().toInt()) {
                famosos = it.get("famosos").toString()
                famUser = it.get("mail").toString()
            }
            setText()
        }
    }


    private fun setText(){
        binding.tvDivRanking.setText ( "Divinando  $divinando  $divUser")
        binding.tvDivTilRanking.setText("Div. Tildes  $divTilde  $divTilUser")
        binding.tvEncRanking.setText("Encadenados  $encadenados  $encUser")
        binding.tvPaisRanking.setText("Country Guesser  $paises  $paisUser")
        binding.tvEscudoRanking.setText("Guess It  $escudos  $escUser")
        binding.tvFamRanking.setText("4 Chooser  $famosos  $famUser")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}