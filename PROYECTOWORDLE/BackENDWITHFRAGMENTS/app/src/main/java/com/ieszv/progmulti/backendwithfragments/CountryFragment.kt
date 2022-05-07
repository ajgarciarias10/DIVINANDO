package com.ieszv.progmulti.backendwithfragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.ieszv.progmulti.backendwithfragments.databinding.FragmentCountryBinding


class CountryFragment : Fragment() {


    //region Contenedor que inicializamos por que luego mas tarde lo vamos a inflar para meterle una vista
        private lateinit var container: LinearLayout
    //endregion
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
             * El dato que devuelve el documento es de tipo HashMap
             *  clave => valor
             *   Ejemplo:
             *   Paises => España
             */

            //region Esto te devuelve la lista de paises en español y lo parseo arrayList ya que arriba he inicializado un ArrayList
                listaDePaisesEnEspañol = document.data?.get("Paises") as ArrayList<String>
            //endregion
            //region Esto te devuelve la lista de paises en ingles y lo parseo arrayList ya que arriba he inicializado un ArrayList
                listaDePaisesEnIngles = document.data?.get("EnglishCountries") as ArrayList<String>
            //endregion
            //region URLS DE LAS IMAGENES
                urls = document.data?.get("urls") as ArrayList<String>
            //endregion
            //region indice aleatorio para coger un pais aleatorio
                val index = (Math.random() * listaDePaisesEnIngles.size).toInt()
            //endregion
            //region cogemos un pais en español
                var paisEnEspañol = listaDePaisesEnEspañol.get(index)
            //endregion
            //region mostramos el país como imagen
                Glide.with(requireContext()).load(urls.get(index)).into(binding.imageView4)
            //endregion
            //region duplicamos la vista duplicate this para asi poder tener dependiendo de la cantidad de letras de pais su cantidad correspondiente
                 for (i in 0..paisEnEspañol.length) {
                     val duplicateThis = LayoutInflater.from(requireContext())
                         .inflate(R.layout.duplicate_this, null) as RelativeLayout
                     container.addView(duplicateThis)

                }
            //endregion
            binding.btPrueba.setOnClickListener {

            }




}




}
}