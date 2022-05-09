package com.ieszv.progmulti.backendwithfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ieszv.progmulti.backendwithfragments.databinding.FragmentFirstBinding
import com.ieszv.progmulti.backendwithfragments.databinding.FragmentPantallaLogueadoBinding

class PantallaLogueado : Fragment() {

    //Inicializamos la variable del cliente de google
    private  lateinit var  mFirebaseAuth: FirebaseAuth
    //Variable para hacer el findByViewID
    private var _binding: FragmentPantallaLogueadoBinding? = null
    private val binding get() = _binding!!

    /**
     * Metodo en el momento de crear la vista
     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPantallaLogueadoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //AUTORIZAMOS A FIREBASE
        mFirebaseAuth = Firebase.auth
        binding.btLogout.setOnClickListener {
            findNavController().navigate(R.id.action_pantallaLogueado_to_FirstFragment)
            mFirebaseAuth.signOut()
        }
        binding.btWorldQuiz.setOnClickListener{
            findNavController().navigate(R.id.action_pantallaLogueado_to_countryFragment)
        }




    }




}