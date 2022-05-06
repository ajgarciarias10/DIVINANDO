package com.ieszv.progmulti.backendwithfragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.ieszv.progmulti.backendwithfragments.databinding.FragmentCountryBinding


class CountryFragment : Fragment() {


    //Contenedor que inicializamos por que luego mas tarde lo vamos a inflar para meterle una vista
    private lateinit var container: LinearLayout

    //region Lista de Paises en el idioma español,ingles y sus respectivas urls
        var listaDePaisesEnEspañol = ArrayList<String>()
        var listaDePaisesEnIngles = ArrayList<String>()
        var urls = ArrayList<String>()
    //endregion
    //region Variable para hacer el findByViewID
        private var _binding: FragmentCountryBinding? = null
        private val binding get() = _binding!!
    //endregion
    //region Cogiendo la instancia de la bd de Firestore
        private val db = FirebaseFirestore.getInstance()
    //endregion

    /**
     * Metodo  en el momento de crear la vista
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root


    }
    /**
     * Metodo al crearse la vista
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        despliegaEditText()
    }


    fun despliegaEditText() {
        container = binding.linear
        db.collection("Paises").document("ListaPaises").get().addOnSuccessListener { document ->

            /**
             * He convertido
             */
            listaDePaisesEnEspañol = document.data?.get("Paises") as ArrayList<String>
            listaDePaisesEnEspañol.forEach { entry ->
                //Log.v("Paises",entry)
            }


            listaDePaisesEnIngles = document.data?.get("EnglishCountries") as ArrayList<String>
            listaDePaisesEnIngles.forEach { entry ->

                //Log.v("Paises Ingleses",entry)
            }
            urls = document.data?.get("Url") as ArrayList<String>

            val index = (Math.random() * listaDePaisesEnIngles.size).toInt()
            var pais = listaDePaisesEnIngles.get(index)
            Log.v("Paises ",pais)
            listaDePaisesEnIngles.get(index)
            Log.v("Paises ",index.toString())



             for (i in 0 until pais.length) {
                 val duplicateThis = LayoutInflater.from(requireContext())
                     .inflate(R.layout.duplicate_this, null) as FrameLayout
                 container.addView(duplicateThis)



            }



}




}
}