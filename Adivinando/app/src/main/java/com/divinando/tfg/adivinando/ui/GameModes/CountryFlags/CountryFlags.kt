package com.divinando.tfg.adivinando.ui.GameModes.CountryFlags

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.divinando.tfg.adivinando.R
import com.divinando.tfg.adivinando.databinding.FragmentCountryFlagsBinding
import com.divinando.tfg.adivinando.databinding.FragmentNormalModeBinding
import com.divinando.tfg.adivinando.model.entity.GameObjeto
import com.divinando.tfg.adivinando.ui.MainActivity
import java.util.regex.Pattern

class CountryFlags : Fragment() {

    private var _binding: FragmentCountryFlagsBinding? = null
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
        _binding = FragmentCountryFlagsBinding.inflate(inflater, container, false)

        return binding.root
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

        var palabraDiccionario =" "
        var paisimagen = " "
        //region obtencion del pais
        do {
            val index = (Math.random() * objeto.listaPalabras!!.size).toInt()
             palabraDiccionario = objeto.listaPalabras!![index].trim()
             paisimagen = objeto.imagenUrl!![index].trim()

        } while (palabraDiccionario.trim().length != 5 && palabraDiccionario.trim().length != 6)
        //endregion
        Log.v("pais",palabraDiccionario)
        Glide.with(requireActivity()).load(paisimagen)
            .override(80,80).error(R.mipmap.ic_launcher).into(binding.imageView2)



        //Caso de que la longitud sea de 5 letras
        if (palabraDiccionario.length == 5){
            //region fase1aFila
            binding.group5pl1.visibility = View.VISIBLE
            //URBANO AQUI METE LO DEL GOO
            binding.f5p.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(estaLaPalabraVacia(binding.f5p9.text,binding.f5p8.text,binding.f5p7.text,binding.f5p6.text,binding.f5p.text,null)){
                        Toast.makeText(requireContext(),"Escriba la palabra correctamente", Toast.LENGTH_LONG).show()
                    }else {
                        compruebafilas(palabraDiccionario,binding.f5p9,binding.f5p8,binding.f5p7,binding.f5p6,binding.f5p,null,0,binding.group5pl2)
                    }
                    true
                }
                false
            }

            //endregion
            // region fase2aFila
            //URBANO AQUI METE LO DEL GOO
            binding.f5p14.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(estaLaPalabraVacia(binding.f5p10.text,binding.f5p11.text,binding.f5p12.text,binding.f5p13.text,binding.f5p14.text,null)){
                        Toast.makeText(requireContext(),"Escriba la palabra correctamente", Toast.LENGTH_LONG).show()
                    }else {
                        compruebafilas(palabraDiccionario,binding.f5p10,binding.f5p11,binding.f5p12,binding.f5p13,binding.f5p14,null,0,binding.group5pl3)

                    }
                    true
                }
                false
            }

            //endregion
            // region fase3aFila
            //URBANO AQUI METE LO DEL GOO
            binding.f5p19.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(estaLaPalabraVacia(binding.f5p15.text,binding.f5p16.text,binding.f5p17.text,binding.f5p18.text,binding.f5p19.text,null)) {
                        Toast.makeText(requireContext(),"Escriba la palabra correctamente", Toast.LENGTH_LONG).show()
                    }else{
                        compruebafilas(palabraDiccionario,binding.f5p15,binding.f5p16,binding.f5p17,binding.f5p18,binding.f5p19,null,0,null)
                    }
                    true
                }
                false
            }
            //endregion
        //Caso de que la longitud sea de 6 letras
        }else {
            //region fase1aFila
            //URBANO AQUI METE LO DEL GOO
            binding.group6pl1.visibility = View.VISIBLE
            binding.f5p25.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(estaLaPalabraVacia(binding.f5p20.text,binding.f5p21.text,binding.f5p22.text,binding.f5p23.text,binding.f5p24.text,binding.f5p25.text)) {
                        Toast.makeText(requireContext(),"Escriba la palabra correctamente", Toast.LENGTH_LONG).show()
                    }else{
                        compruebafilas(palabraDiccionario,binding.f5p20,binding.f5p21,binding.f5p22,binding.f5p23,binding.f5p24,binding.f5p25,1,binding.group6pl2)
                    }
                    true
                }
                false
            }
            //endregion

            // region fase2aFila
            //URBANO AQUI METE LO DEL GOO
            binding.f5p36.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(estaLaPalabraVacia(binding.f5p31.text,binding.f5p32.text,binding.f5p33.text,binding.f5p34.text,binding.f5p35.text,binding.f5p36.text)){
                        Toast.makeText(requireContext(),"Escriba la palabra correctamente", Toast.LENGTH_LONG).show()
                    }else{
                        compruebafilas(palabraDiccionario,binding.f5p31,binding.f5p32,binding.f5p33,binding.f5p34,binding.f5p35,binding.f5p36,1,binding.group6pl3)
                    }
                    true
                }
                false
            }
            //endregion

            // region fase3aFila
            //URBANO AQUI METE LO DEL GOO
            binding.f5p42.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(estaLaPalabraVacia(binding.f5p37.text,binding.f5p38.text,binding.f5p39.text,binding.f5p40.text,binding.f5p41.text,binding.f5p42.text)){
                        Toast.makeText(requireContext(),"Escriba la palabra correctamente", Toast.LENGTH_LONG).show()
                    }else{
                        compruebafilas(palabraDiccionario,binding.f5p37,binding.f5p38,binding.f5p39,binding.f5p40,binding.f5p41,binding.f5p42,1,null)
                    }
                    true
                }
                false
            }
            //endregion



        }

    }
    //region METODO PARA COMPROBAR LAS FILAS!!!
    fun compruebafilas(
        palabraDiccionario: String,
        letra1: EditText,
        letra2: EditText,
        letra3: EditText,
        letra4: EditText,
        letra5: EditText,
        letra6: EditText?,
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

                //binding.btSiguiente2.visibility = View.VISIBLE
                //binding.btTerminar2.visibility = View.VISIBLE

                binding.buttonsLayoutFlag.visibility = View.VISIBLE
                binding.tvLoseFlag.visibility = View.GONE
                binding.tvWinFlag.visibility = View.VISIBLE

                points += 5

                binding.btSiguiente2.setOnClickListener {
                    siguiente(true)
                }
                binding.btTerminar2.setOnClickListener {
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
                    //binding.btSiguiente2.visibility = View.VISIBLE
                    //binding.btTerminar2.visibility = View.VISIBLE

                    binding.buttonsLayoutFlag.visibility = View.VISIBLE
                    binding.tvWinFlag.visibility = View.GONE
                    binding.tvLoseFlag.visibility = View.VISIBLE

                    binding.btSiguiente2.setOnClickListener {
                        siguiente(true)
                    }
                    binding.btTerminar2.setOnClickListener {
                        terminar()

                    }
                }else{
                    letra1.isEnabled = false
                    letra2.isEnabled = false
                    letra3.isEnabled = false
                    letra4.isEnabled = false
                    letra5.isEnabled = false
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

                //binding.btSiguiente2.visibility = View.VISIBLE
                //binding.btTerminar2.visibility = View.VISIBLE

                binding.buttonsLayoutFlag.visibility = View.VISIBLE
                binding.tvLoseFlag.visibility = View.GONE
                binding.tvWinFlag.visibility = View.VISIBLE

                points += 5

                binding.btSiguiente2.setOnClickListener {
                    siguiente(false)
                }
                binding.btTerminar2.setOnClickListener {
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
                    //binding.btSiguiente2.visibility = View.VISIBLE
                    //binding.btTerminar2.visibility = View.VISIBLE

                    binding.buttonsLayoutFlag.visibility = View.VISIBLE
                    binding.tvWinFlag.visibility = View.GONE
                    binding.tvLoseFlag.visibility = View.VISIBLE

                    binding.btSiguiente2.setOnClickListener {
                        siguiente(false)
                    }
                    binding.btTerminar2.setOnClickListener {
                        terminar()

                    }
                }else{
                    letra1.isEnabled = false
                    letra2.isEnabled = false
                    letra3.isEnabled = false
                    letra4.isEnabled = false
                    letra5.isEnabled = false
                    letra6.isEnabled = false
                    group!!.visibility = View.VISIBLE
                }
            }

        }

    }
    //endregion
    //region Metodo de  ver si la palabra coincide 100%
    fun coincidenciaPerfecta(palabraDic:String,palabrafilax:String): Boolean {
        return palabraDic == palabrafilax
    }
    //endregion
    //region Metodo si coincide por posicion para ponerla en verde
    fun coincidenciaPerfectaPorPosicion(palabraDic:String,letra:String,posicion:Int): Boolean {
        return letra == palabraDic[posicion].toString()
    }
    //endregion
    //region Metodo ver si la palabra esta dentro para ponerla en narajuita
    fun coincidenciaEstaDentro(palabraDic:String,letra:String): Boolean{
        return palabraDic.contains(letra)
    }
    //endregion
    //region Metodo para cuando le de al botón siguiente
    fun siguiente(boooleano : Boolean){
        if(boooleano){
            binding.group5pl1.visibility = View.GONE
            binding.group5pl2.visibility = View.GONE
            binding.group5pl3.visibility = View.GONE

            limpiatexto(binding.f5p9,binding.f5p8,binding.f5p7,binding.f5p6,binding.f5p,null)
            limpiatexto(binding.f5p10,binding.f5p11,binding.f5p12,binding.f5p13,binding.f5p14,null)
            limpiatexto(binding.f5p15,binding.f5p16,binding.f5p17,binding.f5p18,binding.f5p19,null)

            //binding.btSiguiente2.visibility = View.GONE
            //binding.btTerminar2.visibility = View.GONE

            binding.buttonsLayoutFlag.visibility = View.GONE

            initialize()

        }else{
            binding.group6pl1.visibility = View.GONE
            binding.group6pl2.visibility = View.GONE
            binding.group6pl3.visibility = View.GONE

            limpiatexto(binding.f5p20,binding.f5p21,binding.f5p22,binding.f5p23,binding.f5p24,binding.f5p25)
            limpiatexto(binding.f5p31,binding.f5p32,binding.f5p33,binding.f5p34,binding.f5p35,binding.f5p36)
            limpiatexto(binding.f5p37,binding.f5p38,binding.f5p39,binding.f5p40,binding.f5p41,binding.f5p42)

            //binding.btSiguiente2.visibility = View.GONE
            //binding.btTerminar2.visibility = View.GONE

            binding.buttonsLayoutFlag.visibility = View.GONE

            initialize()


        }


    }
    //endregion
    //region Método de cuando termine le envie a la pantalla final con sus puntos
    fun terminar(){
        MainActivity.ObjUser.game = "paises"
        MainActivity.ObjUser.point = points.toString()
        findNavController().navigate(R.id.country_toend)
    }
    //endregion
    //region Metodo limpiar texto de cada grupo pasandole la fila
    fun limpiatexto(fila1: EditText, fila2: EditText, fila3: EditText, fila4 : EditText, fila5 : EditText, fila6 : EditText?){

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
    //endregion
    //region Método de si esta la palabra esta vacia
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
    //endregion






}