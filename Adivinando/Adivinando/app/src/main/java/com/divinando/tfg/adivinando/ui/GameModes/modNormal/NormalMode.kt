package com.divinando.tfg.adivinando.ui.GameModes.modNormal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.divinando.tfg.adivinando.databinding.FragmentNormalModeBinding
import com.divinando.tfg.adivinando.model.entity.GameObjeto


class NormalMode : Fragment() {

    private var _binding: FragmentNormalModeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var objeto: GameObjeto
    var bundle = Bundle()
    lateinit var palabraDiccionario : String


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

    fun initialize() {
        //Sacamos los argumentos de los objetos
        bundle = requireArguments()
        //Sacamos el objeto que nos pasa en este caso es del modo de juego  normal
        objeto = bundle.getSerializable("juegos") as GameObjeto
        //region cogemos una palabra del diccionario aleatoria
            do {
                //region indice aleatorio para coger un dato aleatorio
                    val index = (Math.random() * objeto.listaPalabras!!.size).toInt()
                //endregion
                 palabraDiccionario = objeto.listaPalabras!![index]

             } while (palabraDiccionario.length == 6 && palabraDiccionario.length == 7)
        //endregion

        Log.v("Palabra",palabraDiccionario)

        if (palabraDiccionario.length == 6){

            binding.group5palabras.visibility = View.VISIBLE
            cinLetr(palabraDiccionario)

        }else {
            binding.group6pf1.visibility = View.VISIBLE
            seisletr(palabraDiccionario)
        }

    }
    fun cinLetr(palabraDiccionario:String){
        binding.btNext.setOnClickListener {
            var palabrafila1 = binding.f1p1.text.toString() +
                               binding.f1p2.text.toString() +
                               binding.f1p3.text.toString() +
                               binding.f1p4.text.toString() +
                               binding.f1p5.text.toString()
            if(palabrafila1 == palabraDiccionario){
                //Coincide la palabra
            }
            else{
                //Comprobar letras que coinciden o si no coincidenç
                    //Caso coincidencia en la primera posicion
                if(binding.f1p1.text.toString() == palabraDiccionario.get(1).toString()){

               // }else if(){

                //}else if(){

                }
            }
        }


    }
    fun seisletr(palabraDiccionario:String){


    }


}