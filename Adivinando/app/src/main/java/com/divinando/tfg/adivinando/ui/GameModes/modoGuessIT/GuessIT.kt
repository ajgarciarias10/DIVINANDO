package com.divinando.tfg.adivinando.ui.GameModes.modoGuessIT

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.divinando.tfg.adivinando.R
import com.divinando.tfg.adivinando.databinding.FragmentGuessITBinding
import com.divinando.tfg.adivinando.model.entity.GameObjeto
import com.divinando.tfg.adivinando.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlin.random.Random

class GuessIT : Fragment() {

    private var _binding: FragmentGuessITBinding? = null
    private val binding get() = _binding!!
    //nombre e imagen del escudo
    lateinit var imageName: String
    lateinit var name: String

    //data
    lateinit var listado:  MutableList<DocumentSnapshot>
    lateinit var usados:  MutableList<Int>
    var bundle = Bundle()
    //objeto del juego
    lateinit var objeto: GameObjeto
    //Python
    lateinit var py: Python
    lateinit var pyObj: PyObject
    //variables del juego
    lateinit var answer: String
    var round = 1
    var points = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGuessITBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        //Avanza de ronda
        binding.btNextClub.setOnClickListener {
            isEnd(view)
        }
        //Asigna como respuesta TRUE
        binding.btTrue.setOnClickListener {
            answer = R.string.true_.toString()
            answer(0)
        }
        //Asigna como respuesta FALSE
        binding.btFalse.setOnClickListener {
            answer = R.string.false_.toString()
            answer(1)
        }

    }
    //Asigna estilo a la respuesta seleccionada
    private fun answer(i: Int){
        //resetea el estilo de los botones
        binding.btTrue.setBackgroundColor(Color.TRANSPARENT)
        binding.btTrue.setTextColor(Color.GREEN)
        binding.btFalse.setBackgroundColor(Color.TRANSPARENT)
        binding.btFalse.setTextColor(Color.RED)

        //Asigna estilo al boton seleccionado
        if(i == 0){
            binding.btTrue.setBackgroundColor(Color.GREEN)
            binding.btTrue.setTextColor(Color.WHITE)
        }
        else if(i == 1){
            binding.btFalse.setBackgroundColor(Color.RED)
            binding.btFalse.setTextColor(Color.WHITE)
        }
    }
    //Comprueba si se acerto el verdadero o falso y gestiona la puntuacion
    private fun comparation(){
        if(imageName == name && answer == R.string.true_.toString()){
            points++
        }
        else if(imageName != name && answer == R.string.false_.toString()){
            points++
        }
    }
    //Comprueba si el escudo ya salio antes
    private fun foundIdDB(id: Int): Boolean{
        var i = 0
        while(i < usados.size){
            if(id == usados[i]){
                return true
            }
            i++
        }
        return false
    }
    //Obtiene nombre e imagen de 2 escudos
    private fun getClub(){
        //Se elige al azar si la respuesta va a ser verdadera o falsa
        var num = Random.nextInt(0, 100)
        var id1 = RandomNum()
        var id2 = RandomNum()

        //Si el nombre e imagen corresponden
        if(num < 50 ) { //respuesta verdadera
            //Asigna nombre e imagen y añade la id a la lista de usados
            Picasso.get().load(listado[id1].get("url").toString()).into(binding.iClub)
            imageName = listado[id1].get("nombre").toString()
            name = imageName
            binding.tvClubName.text = imageName
            usados.add(id1)
        }
        //Si el nombre e imagen no corresponden
        else{ //respuesta falsa
            //Añade nombre e imagen  y añade la id1 solo a la lista de usados
            Picasso.get().load(listado[id1].get("url").toString()).into(binding.iClub)
            imageName = listado[id1].get("nombre").toString() //imagen de la id 1
            name = listado[id2].get("nombre").toString() // nombre de la id 2
            binding.tvClubName.text = name
            usados.add(id1)
        }
    }
    //Metodo que devuelve un objeto python con funciones cargadas de un archivo
    private fun getPythonFile(name: String): PyObject{
        if (! Python.isStarted()) {
            Python.start( AndroidPlatform(requireContext()));
        }
        py = Python.getInstance()
        var pytobj = py.getModule(name) //give python script name
        return pytobj
    }

    private fun init(){
        listado = mutableListOf()
        usados = mutableListOf()

        bundle = requireArguments()
        //Sacamos el objeto que nos pasa en este caso es del modo de juego  normal
        objeto = bundle.getSerializable("juegos") as GameObjeto
        listado = objeto.listaUrl!!

        binding.tvRound.text = "ROUND $round     $points Points"
        answer = ""
        binding.btTrue.setBackgroundColor(Color.TRANSPARENT)
        binding.btFalse.setBackgroundColor(Color.TRANSPARENT)
        //Obtiene escudo
        getClub()

    }
    //Comprueba si pasa de ronda
    private fun isEnd(view: View){

        if(round < 5){
            //Comprueba respuesta sin elegir
            if(answer == ""){
                Snackbar.make(view, "Select your answer before to next", Snackbar.LENGTH_LONG).setAction("hidden") {}.show()
            }
            else{
                //Comprueba la respuesta seleccionada
                comparation()
                //pasa de ronda
                nextRound()
            }

        }
        else if(round == 5){
            //Resultado final del juego
            binding.btTrue.visibility = View.INVISIBLE
            binding.btFalse.visibility = View.INVISIBLE
            comparation()
            pyObj = getPythonFile("setText")
            binding.tvRound.text = pyObj.callAttr("main",points, round).toString()
            round++
        }
        else{
            //Asigna juego y puntuacion al objeto del juego y viaja al siguiente fragment
            MainActivity.ObjUser.game = "escudos"
            MainActivity.ObjUser.point = points.toString()
            findNavController().navigate(R.id.guess_toend)
        }
    }
    //Restablece la respuesta y obtiene nuevo escudo
    private fun nextRound(){
        round++
        answer = ""
        pyObj = getPythonFile("setText")
        binding.tvRound.text = pyObj.callAttr("main",points, round).toString()
        answer(2)
        getClub()
    }

    private fun RandomNum(): Int{
        var numId = -1
        var found = 0

        pyObj = getPythonFile("validateNum") //python file name == "validateNum"
        numId = pyObj.callAttr("main",(listado.size- 1)).toInt() //python def name == "main"

        //mientras coincida numId con algun Id de la lista de usados
        while(found == 0) {
            numId = pyObj.callAttr("main", (listado.size - 1)).toInt()
            if (!foundIdDB(numId)) {
                found = 1
            }
        }
        return numId
    }

}