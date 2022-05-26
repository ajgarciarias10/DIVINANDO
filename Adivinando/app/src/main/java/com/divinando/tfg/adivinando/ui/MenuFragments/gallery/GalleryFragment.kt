package com.divinando.tfg.adivinando.ui.MenuFragments.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.divinando.tfg.adivinando.databinding.FragmentGalleryBinding
import com.google.firebase.firestore.FirebaseFirestore

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

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
        val db = FirebaseFirestore.getInstance()
        db.collection("Usuarios").get().addOnSuccessListener {
            readUsers(it.documents.size)
            Log.v("XYZYX", "$divUser  $divTilUser  $encUser   $paisUser   $escUser   $famUser ")
        }

    }

    private fun getData(i: String) {
        db.collection("Usuarios").document(i).get().addOnSuccessListener {
            if (divinando.toInt() < it.get("divinando").toString().toInt()) {
                divinando = it.get("divinando").toString()
                divUser = it.get("nombre").toString()
            }
            if (divTilde.toInt() < it.get("divtildes").toString().toInt()) {
                divTilde = it.get("divtildes").toString()
                divTilUser = it.get("nombre").toString()
            }
            if (encadenados.toInt() < it.get("encadenados").toString().toInt()) {
                encadenados = it.get("encadenados").toString()
                encUser = it.get("nombre").toString()
            }
            if (paises.toInt() < it.get("paises").toString().toInt()) {
                paises = it.get("paises").toString()
                paisUser = it.get("nombre").toString()
            }
            if (escudos.toInt() < it.get("escudos").toString().toInt()) {
                escudos = it.get("escudos").toString()
                escUser = it.get("nombre").toString()
            }
            if (famosos.toInt() < it.get("famosos").toString().toInt()) {
                famosos = it.get("famosos").toString()
                famUser = it.get("nombre").toString()
            }
            setText()
        }
    }

    private fun readUsers(size: Int){
        var i = 0
        while(i in 0 until size) {
            getData(i.toString())
            i++
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