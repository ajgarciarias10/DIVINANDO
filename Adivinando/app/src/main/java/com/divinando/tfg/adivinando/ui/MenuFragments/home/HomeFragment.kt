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
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    var userName = "Sonia"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUser()

    }

    private fun getRecord(s: String, t: TextView){
        t.text = "0"
        db.collection("Usuarios").document(MainActivity.ObjUser.id).get().addOnSuccessListener {
            t.text = s.uppercase()+"    "+it.get(s).toString()
        }
    }

    private fun getUser(){
        db.collection("Usuarios").get().addOnSuccessListener {
            var i = 0
            var size = it.documents.size
            while(i in 0 until size){
                db.collection("Usuarios").document(i.toString()).get().addOnSuccessListener {
                    if(MainActivity.ObjUser.mail == it.get("mail").toString()) {
                        MainActivity.ObjUser.name = it.get("nombre").toString()
                        MainActivity.ObjUser.id = it.get("id").toString()
                    }
                    if (MainActivity.ObjUser.id != "") {
                        getRecord("divinando", binding.tvDivHome)
                        getRecord("divtildes", binding.tvDivTilHome)
                        getRecord("encadenados", binding.tvEncHome)
                        getRecord("paises", binding.tvPaisHome)
                        getRecord("escudos", binding.tvEscudoHome)
                        getRecord("famosos", binding.tvFamhome)
                    }
                    else{
                        binding.tvDivHome.text = "0"
                        binding.tvDivTilHome.text = "0"
                        binding.tvEncHome.text = "0"
                        binding.tvPaisHome.text = "0"
                        binding.tvEscudoHome.text = "0"
                        binding.tvFamhome.text = "0"
                    }
                }
                i++
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}