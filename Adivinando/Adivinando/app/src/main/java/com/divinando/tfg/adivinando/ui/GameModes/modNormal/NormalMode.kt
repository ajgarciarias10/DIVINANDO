package com.divinando.tfg.adivinando.ui.GameModes.modNormal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.divinando.tfg.adivinando.R
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
        initialize(view)
    }


    fun initialize(view: View) {
        val palabraDiccionario = getWordFromDict()
        if (palabraDiccionario.length == 5){
            //region fase1aFila
                binding.group5palabras.visibility = View.VISIBLE
                binding.fabLinea15l.setOnClickListener {
                    compruebafilas(palabraDiccionario,binding.f1p1,binding.f1p2,binding.f1p3,binding.f1p4,binding.f1p5,null,0,binding.group5p2aFila)
                }
            //endregion
            // region fase2aFila
                binding.fabLinea15l2.setOnClickListener {
                    compruebafilas(palabraDiccionario,binding.f2p1,binding.f2p2,binding.f2p3,binding.f2p4,binding.f2p5,null,0,binding.group5p3afila)
                }
            //endregion
            // region fase3aFila
            binding.fabLinea15l3.setOnClickListener {
                compruebafilas(palabraDiccionario,binding.f3p1,binding.f3p2,binding.f3p3,binding.f3p4,binding.f3p5,null,0,binding.group5p4fila)
            }
            //endregion
            // region fase4aFila
            binding.fabLinea15l4.setOnClickListener {
                compruebafilas(palabraDiccionario,binding.f4p1,binding.f4p2,binding.f4p3,binding.f4p4,binding.f4p5,null,0,binding.group5p5fila)
            }
            //endregion
            // region fase5aFila
            binding.fabLinea15l5.setOnClickListener {
                compruebafilas(palabraDiccionario,binding.f5p1,binding.f5p2,binding.f5p3,binding.f5p4,binding.f5p5,null,0,null)
            }
            //endregion


        }else {
            /*
            //region fase1aFila
            binding.group6pf1.visibility = View.VISIBLE
            binding.fabLinea15l.setOnClickListener {
                compruebafilas(palabraDiccionario,binding.f1p1,binding.f1p2,binding.f1p3,binding.f1p4,binding.f1p5,null,0,binding.group5p2aFila)
            }
            //endregion
            // region fase2aFila
            binding.fabLinea15l2.setOnClickListener {
                compruebafilas(palabraDiccionario,binding.f2p1,binding.f2p2,binding.f2p3,binding.f2p4,binding.f2p5,null,0,binding.group5p3afila)
            }
            //endregion
            // region fase3aFila
            binding.fabLinea15l3.setOnClickListener {
                compruebafilas(palabraDiccionario,binding.f3p1,binding.f3p2,binding.f3p3,binding.f3p4,binding.f3p5,null,0,binding.group5p4fila)
            }
            //endregion
            // region fase4aFila
            binding.fabLinea15l4.setOnClickListener {
                compruebafilas(palabraDiccionario,binding.f4p1,binding.f4p2,binding.f4p3,binding.f4p4,binding.f4p5,null,0,binding.group5p5fila)
            }
            //endregion
            // region fase5aFila
            binding.fabLinea15l5.setOnClickListener {
                compruebafilas(palabraDiccionario,binding.f5p1,binding.f5p2,binding.f5p3,binding.f5p4,binding.f5p5,null,0,null)
            }
            //endregion
            */



        }

    }

    fun compruebafilas(
        palabraDiccionario: String,
        letra1:EditText,
        letra2:EditText,
        letra3:EditText,
        letra4:EditText,
        letra5:EditText,
        letra6:EditText?,
        switch:Int,
        group: Group?){
          if(switch == 0){
              val palabra1fila = letra1.text.toString() +
                      letra2.text +
                      letra3.text +
                      letra4.text +
                      letra5.text
              if(coincidenciaPerfecta(palabraDiccionario,palabra1fila)){
                  letra1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                  letra2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                  letra3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                  letra4.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                  letra5.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                  letra1.isEnabled = false
                  letra2.isEnabled = false
                  letra3.isEnabled = false
                  letra4.isEnabled = false
                  letra5.isEnabled = false

                  binding.btSiguiente.visibility = View.GONE
                  binding.btTerminar.visibility = View.GONE

                  binding.btSiguiente.setOnClickListener {
                        siguiente(true)
                  }
                  binding.btTerminar.setOnClickListener {
                        terminar()
                  }

              }
              else {
                  if (coincidenciaPerfectaPorPosicion(
                          palabraDiccionario,
                          letra1.text.toString(),
                          0
                      )
                  ) {
                      letra1.setBackgroundColor(
                          ContextCompat.getColor(
                              requireContext(),
                              R.color.correctletter
                          )
                      )
                      letra1.isEnabled = false
                  } else {
                      if (coincidenciaEstaDentro(palabraDiccionario, letra1.text.toString())) {
                          letra1.setBackgroundColor(
                              ContextCompat.getColor(
                                  requireContext(),
                                  R.color.positionletter
                              )
                          )
                          letra1.isEnabled = false
                      }
                  }
                  if (coincidenciaPerfectaPorPosicion(
                          palabraDiccionario,
                          letra2.text.toString(),
                          1
                      )
                  ) {
                      letra2.setBackgroundColor(
                          ContextCompat.getColor(
                              requireContext(),
                              R.color.correctletter
                          )
                      )
                      letra2.isEnabled = false
                  } else {
                      if (coincidenciaEstaDentro(palabraDiccionario, letra2.text.toString())) {
                          letra2.setBackgroundColor(
                              ContextCompat.getColor(
                                  requireContext(),
                                  R.color.positionletter
                              )
                          )
                          letra2.isEnabled = false
                      }
                  }
                  if (coincidenciaPerfectaPorPosicion(
                          palabraDiccionario,
                          letra3.text.toString(),
                          2
                      )
                  ) {
                      letra3.setBackgroundColor(
                          ContextCompat.getColor(
                              requireContext(),
                              R.color.correctletter
                          )
                      )
                      letra3.isEnabled = false
                  } else {
                      if (coincidenciaEstaDentro(palabraDiccionario, letra3.text.toString())) {
                          letra3.setBackgroundColor(
                              ContextCompat.getColor(
                                  requireContext(),
                                  R.color.positionletter
                              )
                          )
                          letra3.isEnabled = false
                      }
                  }
                  if (coincidenciaPerfectaPorPosicion(
                          palabraDiccionario,
                          letra4.text.toString(),
                          3
                      )
                  ) {
                      letra4.setBackgroundColor(
                          ContextCompat.getColor(
                              requireContext(),
                              R.color.correctletter
                          )
                      )
                      letra4.isEnabled = false
                  } else {
                      if (coincidenciaEstaDentro(palabraDiccionario, letra4.text.toString())) {
                          letra4.setBackgroundColor(
                              ContextCompat.getColor(
                                  requireContext(),
                                  R.color.positionletter
                              )
                          )
                          letra4.isEnabled = false
                      }
                  }
                  if (coincidenciaPerfectaPorPosicion(
                          palabraDiccionario,
                          letra5.text.toString(),
                          4
                      )
                  ) {
                      letra5.setBackgroundColor(
                          ContextCompat.getColor(
                              requireContext(),
                              R.color.correctletter
                          )
                      )
                      letra5.isEnabled = false
                  } else {
                      if (coincidenciaEstaDentro(palabraDiccionario, letra5.text.toString())) {
                          letra5.setBackgroundColor(
                              ContextCompat.getColor(
                                  requireContext(),
                                  R.color.positionletter
                              )
                          )
                          letra5.isEnabled = false
                      }
                  }
                  group!!.visibility = View.VISIBLE

                  binding.btSiguiente.visibility = View.GONE
                  binding.btTerminar.visibility = View.GONE

                  binding.btSiguiente.setOnClickListener {
                      siguiente(true)
                  }
                  binding.btTerminar.setOnClickListener {
                      terminar()
                  }

              }
          }else{
              val palabra1fila = letra1.text.toString() +
                      letra2.text +
                      letra3.text +
                      letra4.text +
                      letra5.text +
                      letra6!!.text
              if(coincidenciaPerfecta(palabraDiccionario,palabra1fila)){
                  letra1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                  letra2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                  letra3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                  letra4.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                  letra5.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                  letra6.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                  letra1.isEnabled = false
                  letra2.isEnabled = false
                  letra3.isEnabled = false
                  letra4.isEnabled = false
                  letra5.isEnabled = false
                  letra6.isEnabled = false


                  binding.btSiguiente.visibility = View.GONE
                  binding.btTerminar.visibility = View.GONE

                  binding.btSiguiente.setOnClickListener {
                      siguiente(true)
                  }
                  binding.btTerminar.setOnClickListener {
                      terminar()
                  }



              }
              else{
                  if(coincidenciaPerfectaPorPosicion(palabraDiccionario,letra1.text.toString(),0)){
                      letra1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                      letra1.isEnabled = false
                  }
                  else{
                      if(coincidenciaEstaDentro(palabraDiccionario,letra1.text.toString())){
                          letra1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.positionletter))
                          letra1.isEnabled = false
                      }
                  }
                  if(coincidenciaPerfectaPorPosicion(palabraDiccionario,letra2.text.toString(),1)){
                      letra2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                      letra2.isEnabled = false
                  }else{
                      if(coincidenciaEstaDentro(palabraDiccionario,letra2.text.toString())){
                          letra2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.positionletter))
                          letra2.isEnabled = false
                      }
                  }
                  if(coincidenciaPerfectaPorPosicion(palabraDiccionario,letra3.text.toString(),2)){
                      letra3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                      letra3.isEnabled = false
                  }
                  else{
                      if(coincidenciaEstaDentro(palabraDiccionario,letra3.text.toString())){
                          letra3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.positionletter))
                          letra3.isEnabled = false
                      }
                  }
                  if(coincidenciaPerfectaPorPosicion(palabraDiccionario,letra4.text.toString(),3)){
                      letra4.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                      letra4.isEnabled = false
                  }else{
                      if(coincidenciaEstaDentro(palabraDiccionario,letra4.text.toString())){
                          letra4.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.positionletter))
                          letra4.isEnabled = false
                      }
                  }
                  if(coincidenciaPerfectaPorPosicion(palabraDiccionario,letra5.text.toString(),4)) {
                      letra5.setBackgroundColor(
                          ContextCompat.getColor(
                              requireContext(),
                              R.color.correctletter
                          )
                      )
                      letra5.isEnabled = false
                  }
                  else{
                      letra5.setBackgroundColor(
                          ContextCompat.getColor(
                              requireContext(),
                              R.color.positionletter
                          )
                      )
                      letra5.isEnabled = false

                  }
                  if(coincidenciaPerfectaPorPosicion(palabraDiccionario,letra6.text.toString(),0)){
                      letra6.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                      letra6.isEnabled = false
                  }
                  else{
                      if(coincidenciaEstaDentro(palabraDiccionario,letra6.text.toString())){
                          letra6.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.positionletter))
                          letra6.isEnabled = false
                      }
                  }
                  group!!.visibility = View.VISIBLE
                  binding.btSiguiente.visibility = View.GONE
                  binding.btTerminar.visibility = View.GONE

                  binding.btSiguiente.setOnClickListener {
                      siguiente(true)
                  }
                  binding.btTerminar.setOnClickListener {
                      terminar()
                  }

              }



          }

    }



    fun getWordFromDict(): String {
        var palabraDiccionario = ""
        //Sacamos los argumentos de los objetos
        bundle = requireArguments()
        //Sacamos el objeto que nos pasa en este caso es del modo de juego  normal
        objeto = bundle.getSerializable("juegos") as GameObjeto
        //region Extraer del diccionario palabras de 5 a 6 letras sin tildes
        do {
            //Expresion regular para comprobar si la palabra contiene tildes
            val regex = Regex("/^[a-zA-Z\\u00C0-\\u017F]+,\\s[a-zA-Z\\u00C0-\\u017F]+\$/");
            //region indice aleatorio para coger un dato aleatorio
            val index = (Math.random() * objeto.listaPalabras!!.size).toInt()
            //endregion
            palabraDiccionario = objeto.listaPalabras!![index].trim()

        } while (palabraDiccionario.contains(regex)&& palabraDiccionario.length == 6 && palabraDiccionario.length == 7)
        //endregion
        Log.v("Palabra",palabraDiccionario)
        return palabraDiccionario
    }
    fun coincidenciaPerfecta(palabraDic:String,palabrafilax:String): Boolean {
        return palabraDic == palabrafilax
    }
    fun coincidenciaPerfectaPorPosicion(palabraDic:String,letra:String,posicion:Int): Boolean {
        return letra == palabraDic[posicion].toString()
    }
    fun coincidenciaEstaDentro(palabraDic:String,letra:String): Boolean{
        return palabraDic.contains(letra)
    }
    fun siguiente(boooleano : Boolean){
        if(boooleano){
            binding.group5palabras.visibility = View.GONE
            binding.group5p2aFila.visibility = View.GONE
            binding.group5p3afila.visibility = View.GONE
            binding.group5p4fila.visibility = View.GONE
            binding.group5p5fila.visibility = View.GONE


        }else{
            binding.group6pf1.visibility = View.GONE
            binding.group6pf2.visibility = View.GONE
            binding.group6pf3.visibility = View.GONE
            binding.group6pf4.visibility = View.GONE
            binding.group6pf5.visibility = View.GONE

        }
    }
    fun terminar(){
        findNavController().navigate(R.id.action_normalMode_to_nav_home)
    }




}