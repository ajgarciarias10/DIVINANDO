package com.divinando.tfg.adivinando.ui.GameModes.modNormal

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.divinando.tfg.adivinando.R
import com.divinando.tfg.adivinando.databinding.FragmentNormalModeBinding
import com.divinando.tfg.adivinando.model.entity.GameObjeto
import com.divinando.tfg.adivinando.ui.MainActivity
import java.io.Console
import java.text.Normalizer
import java.util.regex.Pattern


class NormalMode : Fragment() {
    private var _binding: FragmentNormalModeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var objeto: GameObjeto
    var bundle = Bundle()
    var points = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNormalModeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }


    fun initialize() {
        val palabraDiccionario = getWordFromDict()
        //Caso de que la longitud sea de 5 letras
        if (palabraDiccionario.length == 5){
            //region fase1aFila
                binding.group5palabras.visibility = View.VISIBLE
                binding.fabLinea15l.setOnClickListener {
                    if(estaLaPalabraVacia(binding.f1p1.text,binding.f1p2.text,binding.f1p3.text,binding.f1p4.text,binding.f1p5.text,null)){
                        Toast.makeText(requireContext(),"Escriba la palabra correctamente",Toast.LENGTH_LONG).show()
                    }else {
                        compruebafilas(palabraDiccionario,binding.f1p1,binding.f1p2,binding.f1p3,binding.f1p4,binding.f1p5,null,0,binding.group5p2aFila)
                    }


                }
            //endregion
            // region fase2aFila
                binding.fabLinea15l2.setOnClickListener {
                    if(estaLaPalabraVacia(binding.f2p1.text,binding.f2p2.text,binding.f2p3.text,binding.f2p4.text,binding.f2p5.text,null)){
                        Toast.makeText(requireContext(),"Escriba la palabra correctamente",Toast.LENGTH_LONG).show()
                    }else {
                        compruebafilas(
                            palabraDiccionario,
                            binding.f2p1,
                            binding.f2p2,
                            binding.f2p3,
                            binding.f2p4,
                            binding.f2p5,
                            null,
                            0,
                            binding.group5p3afila
                        )
                    }
                }
            //endregion
            // region fase3aFila
            binding.fabLinea15l5.setOnClickListener {
                if(estaLaPalabraVacia(binding.f3p1.text,binding.f3p2.text,binding.f3p3.text,binding.f3p4.text,binding.f3p5.text,null)) {
                    Toast.makeText(requireContext(),"Escriba la palabra correctamente",Toast.LENGTH_LONG).show()
                }else{
                    compruebafilas(
                        palabraDiccionario,
                        binding.f3p1,
                        binding.f3p2,
                        binding.f3p3,
                        binding.f3p4,
                        binding.f3p5,
                        null,
                        0,
                        binding.group5p4fila
                    )
                }
             }
            //endregion
            // region fase4aFila
            binding.fabLinea15l4.setOnClickListener {
                if(estaLaPalabraVacia(binding.f4p1.text,binding.f4p2.text,binding.f4p3.text,binding.f4p4.text,binding.f4p5.text,null)) {
                    Toast.makeText(requireContext(),"Escriba la palabra correctamente",Toast.LENGTH_LONG).show()
                }else{
                    compruebafilas(
                        palabraDiccionario,
                        binding.f4p1,
                        binding.f4p2,
                        binding.f4p3,
                        binding.f4p4,
                        binding.f4p5,
                        null,
                        0,
                        binding.group5p5fila
                    )
                }
            }
            //endregion
            // region fase5aFila
            binding.fabLinea15l3.setOnClickListener {
                if(estaLaPalabraVacia(binding.f5p1.text,binding.f5p2.text,binding.f5p3.text,binding.f5p4.text,binding.f5p5.text,null)) {
                    Toast.makeText(requireContext(),"Escriba la palabra correctamente",Toast.LENGTH_LONG).show()
                }else{
                    compruebafilas(palabraDiccionario,binding.f5p1,binding.f5p2,binding.f5p3,binding.f5p4,binding.f5p5,null,0,null)
                }

            }
            //endregion
        //Caso de que la longitud sea de 6 letras
        }else {
            //region fase1aFila
                binding.group6pf1.visibility = View.VISIBLE
                binding.fabLineal6.setOnClickListener {
                    if(estaLaPalabraVacia(binding.f1p16l.text,binding.f1p26l.text,binding.f1p36l.text,binding.f1p46l.text,binding.f1p56l.text,binding.f1p66l.text)) {
                        Toast.makeText(requireContext(),"Escriba la palabra correctamente",Toast.LENGTH_LONG).show()
                    }else{
                        compruebafilas(palabraDiccionario,binding.f1p16l,binding.f1p26l,binding.f1p36l,binding.f1p46l,binding.f1p56l,binding.f1p66l,1,binding.group6pf2)
                    }

                }
            //endregion

            // region fase2aFila
                binding.fabLineal61.setOnClickListener {
                    if(estaLaPalabraVacia(binding.f2p16l.text,binding.f2p26l.text,binding.f2p36l.text,binding.f2p46l.text,binding.f2p56l.text,binding.f2p66l.text)) {
                        Toast.makeText(requireContext(),"Escriba la palabra correctamente",Toast.LENGTH_LONG).show()
                    }else{
                        compruebafilas(palabraDiccionario,binding.f2p16l,binding.f2p26l,binding.f2p36l,binding.f2p46l,binding.f2p56l,binding.f2p66l,1,binding.group6pf3)
                    }

                }
            //endregion

            // region fase3aFila
                binding.fabLineal62.setOnClickListener {
                    if(estaLaPalabraVacia(binding.f3p16l.text,binding.f3p26l.text,binding.f3p36l.text,binding.f3p46l.text,binding.f3p56l.text,binding.f3p66l.text)){
                        Toast.makeText(requireContext(),"Escriba la palabra correctamente",Toast.LENGTH_LONG).show()
                    }else{
                        compruebafilas(palabraDiccionario,binding.f3p16l,binding.f3p26l,binding.f3p36l,binding.f3p46l,binding.f3p56l,binding.f3p66l,1,binding.group6pf4)
                    }

                }
            //endregion

            // region fase4aFila
                binding.fabLineal63.setOnClickListener {
                    if(estaLaPalabraVacia(binding.f4p16l.text,binding.f4p26l.text,binding.f4p36l.text,binding.f4p46l.text,binding.f4p56l.text,binding.f4p66l.text)){
                        Toast.makeText(requireContext(),"Escriba la palabra correctamente",Toast.LENGTH_LONG).show()
                    }else{
                        compruebafilas(palabraDiccionario,binding.f4p16l,binding.f4p26l,binding.f4p36l,binding.f4p46l,binding.f4p56l,binding.f4p66l,1,binding.group6pf5)
                    }

                }
            //endregion

            // region fase5aFila
                binding.fabLineal65.setOnClickListener {
                    if(estaLaPalabraVacia(binding.f5p16l.text,binding.f5p26l.text,binding.f5p36l.text,binding.f5p46l.text,binding.f5p56l.text,binding.f5p66l.text)){
                        Toast.makeText(requireContext(),"Escriba la palabra correctamente",Toast.LENGTH_LONG).show()
                    }else{
                        compruebafilas(palabraDiccionario,binding.f5p16l,binding.f5p26l,binding.f5p36l,binding.f5p46l,binding.f5p56l,binding.f5p66l,1,null)
                    }

                }
            //endregion

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




        //Caso de que switch operador para cambiar metodo en funcion de la cantidad de letras
            //En este caso switch = 0 si es 5 letras
          if(switch == 0){
              val palabra1fila = letra1.text.toString() +
                      letra2.text +
                      letra3.text +
                      letra4.text +
                      letra5.text
              if(coincidenciaPerfecta(palabraDiccionario.trim(),palabra1fila.trim())){

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

                  binding.btSiguiente.visibility = View.VISIBLE
                  binding.btTerminar.visibility = View.VISIBLE

                  points += 5

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
                      points++
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
                      points++
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
                      points++
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
                      points++
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
                      points++

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
                  if(group == null){
                      binding.btSiguiente.visibility = View.VISIBLE
                      binding.btTerminar.visibility = View.VISIBLE

                      binding.btSiguiente.setOnClickListener {
                          siguiente(true)
                      }
                      binding.btTerminar.setOnClickListener {
                          terminar()

                      }
                  }else{
                      group!!.visibility = View.VISIBLE
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


                      binding.btSiguiente.visibility = View.VISIBLE
                      binding.btTerminar.visibility = View.VISIBLE

                      points += 5

                      binding.btSiguiente.setOnClickListener {
                          siguiente(false)
                      }
                      binding.btTerminar.setOnClickListener {
                          terminar()
                      }



                  }
                  else{
                      if(coincidenciaPerfectaPorPosicion(palabraDiccionario,letra1.text.toString(),0)){
                          letra1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                          letra1.isEnabled = false
                          points++
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
                          points++
                      }else{
                          if(coincidenciaEstaDentro(palabraDiccionario,letra2.text.toString())){
                              letra2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.positionletter))
                              letra2.isEnabled = false
                          }
                      }
                      if(coincidenciaPerfectaPorPosicion(palabraDiccionario,letra3.text.toString(),2)){
                          letra3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctletter))
                          letra3.isEnabled = false
                          points++
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
                          points++
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
                          points++
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
                          points++
                      }
                      else{
                          if(coincidenciaEstaDentro(palabraDiccionario,letra6.text.toString())){
                              letra6.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.positionletter))
                              letra6.isEnabled = false
                          }
                      }
                      if(group == null){
                          binding.btSiguiente.visibility = View.VISIBLE
                          binding.btTerminar.visibility = View.VISIBLE

                          binding.btSiguiente.setOnClickListener {
                              siguiente(true)
                          }
                          binding.btTerminar.setOnClickListener {
                              terminar()

                          }
                      }else{
                          group!!.visibility = View.VISIBLE
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

            val index = (Math.random() * objeto.listaPalabras!!.size).toInt()
            palabraDiccionario = objeto.listaPalabras!![index].trim()
            var tilde = tieneLaPalabraTilde(palabraDiccionario.toLowerCase())
            Log.v("CACA",tilde.toString())
        } while (!tilde || (palabraDiccionario.length == 6 || palabraDiccionario.length == 7))
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

            limpiatexto(binding.f1p1,binding.f1p2,binding.f1p3,binding.f1p4,binding.f1p5,null)
            limpiatexto(binding.f2p1,binding.f2p2,binding.f2p3,binding.f2p4,binding.f2p5,null)
            limpiatexto(binding.f3p1,binding.f3p2,binding.f3p3,binding.f3p4,binding.f3p5,null)
            limpiatexto(binding.f4p1,binding.f4p2,binding.f4p3,binding.f4p4,binding.f4p5,null)
            limpiatexto(binding.f5p1,binding.f5p2,binding.f5p3,binding.f5p4,binding.f5p5,null)


            binding.btSiguiente.visibility = View.GONE
            binding.btTerminar.visibility = View.GONE

            initialize()

        }else{
            binding.group6pf1.visibility = View.GONE
            binding.group6pf2.visibility = View.GONE
            binding.group6pf3.visibility = View.GONE
            binding.group6pf4.visibility = View.GONE
            binding.group6pf5.visibility = View.GONE

            limpiatexto(binding.f1p16l,binding.f1p26l,binding.f1p36l,binding.f1p46l,binding.f1p56l,binding.f1p66l)
            limpiatexto(binding.f2p16l,binding.f2p26l,binding.f2p36l,binding.f2p46l,binding.f2p56l,binding.f2p66l)
            limpiatexto(binding.f3p16l,binding.f3p26l,binding.f3p36l,binding.f3p46l,binding.f3p56l,binding.f3p66l)
            limpiatexto(binding.f4p16l,binding.f4p26l,binding.f4p36l,binding.f4p46l,binding.f4p56l,binding.f4p66l)
            limpiatexto(binding.f5p16l,binding.f5p26l,binding.f5p36l,binding.f5p46l,binding.f5p56l,binding.f5p66l)


            binding.btSiguiente.visibility = View.GONE
            binding.btTerminar.visibility = View.GONE


            initialize()


        }


    }
    fun terminar(){
        MainActivity.ObjUser.game = "divinando"
        MainActivity.ObjUser.point = points.toString()
        findNavController().navigate(R.id.normal_toend)
    }
    fun limpiatexto(fila1: EditText, fila2:EditText, fila3: EditText ,fila4 : EditText, fila5 : EditText, fila6 : EditText?){

        fila1.setText("")
        fila1.setBackgroundColor(Color.WHITE)
        fila1.isEnabled = true

        fila2.setText("")
        fila2.setBackgroundColor(Color.WHITE)
        fila2.isEnabled = true

        fila3.setText("")
        fila3.setBackgroundColor(Color.WHITE)
        fila3.isEnabled = true

        fila4.setText("")
        fila4.setBackgroundColor(Color.WHITE)
        fila4.isEnabled = true

        fila5.setText("")
        fila5.setBackgroundColor(Color.WHITE)
        fila5.isEnabled = true

        fila6?.setText("")
        fila6?.setBackgroundColor(Color.WHITE)
        fila6?.isEnabled = true


    }
    fun estaLaPalabraVacia(
        letra1: Editable,
        letra2: Editable,
        letra3: Editable,
        letra4: Editable,
        letra5: Editable,
        letra6: Editable?
    ):Boolean {
        if (letra6 != null) {
            return letra1.isEmpty() || letra2.isEmpty() || letra3.isEmpty() ||
                    letra4.isEmpty() || letra5.isEmpty() || letra6.isEmpty()
        }else{
            return letra1.isEmpty() || letra2.isEmpty() || letra3.isEmpty() ||
                    letra4.isEmpty() || letra5.isEmpty() 
        }
    }




    fun tieneLaPalabraTilde(palabraDic: String): Boolean {
        val regex = Regex("\\S*[\\u00E0-\\u00FC]\\S*")
        return palabraDic.toLowerCase().matches(regex)
    }
}