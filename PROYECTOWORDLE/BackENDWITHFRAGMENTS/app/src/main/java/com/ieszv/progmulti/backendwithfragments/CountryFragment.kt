package com.ieszv.progmulti.backendwithfragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.ieszv.progmulti.backendwithfragments.databinding.FragmentCountryBinding

class CountryFragment : Fragment() {


    lateinit var sr: StorageReference
    private  lateinit var  container:LinearLayout
    var arraylistPaises = ArrayList<String>()
     var pais = String()
    //Variable para hacer el findByViewID
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!

    private  val db = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        despliegaEditText()
    }

    fun despliegaEditText() {
        container = binding.linear

        db.collection("Paises").document("ListaPaises").get().addOnSuccessListener { document ->
         arraylistPaises.add(document.data?.get("Paises").toString())
        }
        for (i in 0 until arraylistPaises.size) {
            // generating the index using Math.random()
            val index = (Math.random() * arraylistPaises.size) as Int

            pais = arraylistPaises.get(index)

            Log.v(
                "Random Element is :",  arraylistPaises.get(index)
            )
        }
        for (i in 0 until pais.length) {
            val duplicateThis = LayoutInflater.from(requireContext())
                .inflate(R.layout.duplicate_this, null) as LinearLayout
            container.addView(duplicateThis)


        }





    }





}