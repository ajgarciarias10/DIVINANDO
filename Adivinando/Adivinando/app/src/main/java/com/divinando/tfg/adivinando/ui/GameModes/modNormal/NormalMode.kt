package com.divinando.tfg.adivinando.ui.GameModes.modNormal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.divinando.tfg.adivinando.databinding.FragmentNormalModeBinding
import com.divinando.tfg.adivinando.model.entity.GameObjeto


class NormalMode : Fragment() {

    private var _binding: FragmentNormalModeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var objeto: GameObjeto
    var bundle = Bundle()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNormalModeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    fun initialize(){
         bundle = requireArguments()
            objeto = bundle.getSerializable("juegos") as GameObjeto




    }





}