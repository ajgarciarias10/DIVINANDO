package com.divinando.tfg.adivinando.ui.GameModes.encadenados

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.divinando.tfg.adivinando.R
import com.divinando.tfg.adivinando.databinding.FragmentEncadenadosBinding
import com.divinando.tfg.adivinando.model.entity.GameObjeto
import com.divinando.tfg.adivinando.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class Encadenados : Fragment() {

    private var _binding: FragmentEncadenadosBinding? = null
    private val binding get() = _binding!!
    //Objeto del juego
    lateinit var objeto: GameObjeto
    //palabra
    lateinit var word: String
    lateinit var DBword: String
    lateinit var answerText: String

    //Python
    lateinit var py: Python
    lateinit var pyObj: PyObject
    //datos
    lateinit var listado:  ArrayList<String>
    lateinit var  bundle : Bundle
    //variables del juego
    var round = 1
    var points = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEncadenadosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        //Comprueba ronda y gestiona puntuación
        binding.tvChainC5.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                isEnd(view)
                true
            }
            false
        }

        /*binding.btNextChain.setOnClickListener {
            isEnd(view)
        }*/

    }
    //Asigna valor y estilo acada recuadro de texto del layout
    private fun answer(i: Int){
        if(i == 0){
            binding.tvChainA1.text = word[0].toString()
            binding.tvChainA2.text = word[1].toString()
            binding.tvChainA3.text = "_"
            binding.tvChainA4.text = "_"
            binding.tvChainA5.text = "_"
            binding.tvChainA1.setBackgroundResource(R.color.correctchain)
            binding.tvChainA2.setBackgroundResource(R.color.correctchain)
        }
        else if(i == 1){
            binding.tvChainB1.text = DBword[0].toString()
            binding.tvChainB2.text = DBword[1].toString()
            binding.tvChainB3.text = DBword[2].toString()
            binding.tvChainB4.text = DBword[3].toString()
            binding.tvChainB5.text = DBword[4].toString()
            binding.tvChainB1.setBackgroundResource(R.color.correctchain)
            binding.tvChainB2.setBackgroundResource(R.color.correctchain)
            binding.tvChainB3.setBackgroundResource(R.color.correctchain)
            binding.tvChainB4.setBackgroundResource(R.color.correctchain)
            binding.tvChainB5.setBackgroundResource(R.color.correctchain)
            binding.tvChainA1.text = DBword[3].toString()
            binding.tvChainA2.text = DBword[4].toString()
        }
        else if(i == 2){
            binding.tvChainB1.text = binding.tvChainC1.text
            binding.tvChainB2.text = binding.tvChainC2.text
            binding.tvChainB3.text = binding.tvChainC3.text
            binding.tvChainB4.text = binding.tvChainC4.text
            binding.tvChainB5.text = binding.tvChainC5.text
            binding.tvChainB1.setBackgroundResource(R.color.prechain)
            binding.tvChainB2.setBackgroundResource(R.color.prechain)
            binding.tvChainB3.setBackgroundResource(R.color.incorrectchain)
            binding.tvChainB4.setBackgroundResource(R.color.incorrectchain)
            binding.tvChainB5.setBackgroundResource(R.color.incorrectchain)
        }
        binding.tvChainC1.setText("")
        binding.tvChainC2.setText("")
        binding.tvChainC3.setText("")
        binding.tvChainC4.setText("")
        binding.tvChainC5.setText("")
    }
    //Construye la palabra a partir de los recuadros
    private fun buildWord(){
        answerText = ""+binding.tvChainC1.text+binding.tvChainC2.text+
                        binding.tvChainC3.text+binding.tvChainC4.text+binding.tvChainC5.text
        answerText = answerText
    }
    //Comprueba los recuadros de texto para pasar de ronda
    private fun comprobation(view: View){
        if(isEmptyChar()) {
            Snackbar.make(view, "Error  5 length word please.", Snackbar.LENGTH_LONG).setAction("hidden") {}.show()
        }
        else{
            nextRound()
        }
    }
    //Comprueba la palabra y gestiona los puntos segun el resultado
    private fun foundWord(){
        DBword = ""
        var i = 0
        //existe la palabra en DB?
        while (i < listado.size) {
            if (listado[i] == answerText) {
                DBword = answerText // si existe
                i = listado.size //acabar bucle
            }
            i++
        }
        if (DBword != "") {
            answer(1)
            points++
        } else {
            answer(2)
            points--
        }
    }
    //Devuelve un objeto que contiene funciones en un archivo python
    private fun getPythonFile(name: String): PyObject {
        if (! Python.isStarted()) {
            Python.start( AndroidPlatform(requireContext()));
        }
        py = Python.getInstance()
        var pytobj = py.getModule(name) //obtiene el nombre del archivo python
        return pytobj
    }
    private fun init(){
        bundle = requireArguments()
        //Sacamos el objeto que nos pasa en este caso es del modo de juego  normal
        objeto = bundle.getSerializable("juegos") as GameObjeto
        word = ""
        //carga datos
        listado = objeto.listaPalabras!!
        //obtiene una palabra de la lista al azar
        val index = (Math.random() * objeto.listaPalabras!!.size).toInt()
        word = objeto.listaPalabras!![index].trim()
        //Asigna valor y estilo a los recuadros de las palabras
        answer(0)
        loadScreen()
    }
    //Comprueba si la lomgitud de cada recuadro es 1 (True) o distinto (FALSE)
    private fun isEmptyChar(): Boolean{
        if(binding.tvChainC1.length() != 1 || binding.tvChainC2.length() != 1 || binding.tvChainC3.length() != 1 ||
            binding.tvChainC4.length() != 1 || binding.tvChainC5.length() != 1) {
            return true
        }
        return false
    }

    private fun isEnd(view: View){
        //Comprueba si se puede pasar de ronda y en ese caso lo hace
        if(round < 5){
            comprobation(view)
        }
        //Comprueba palabra y asigna texto de juego
        else if(round == 5) {
            foundWord()
            pyObj = getPythonFile("setText")
            binding.tvScore.text = pyObj.callAttr("main",points, round).toString()
            round++
        }
        else{
            //asigna nombre de juego y puntuación al objeto y viaja al siguiente fragment
            MainActivity.ObjUser.game = "encadenados"
            MainActivity.ObjUser.point = points.toString()
            findNavController().navigate(R.id.enc_toend)
        }
    }

    private fun loadScreen(){
        //antes de cargar datos
        binding.GroupA.visibility = View.INVISIBLE
        binding.GroupB.visibility = View.INVISIBLE
        binding.GroupC.visibility = View.INVISIBLE
        binding.tvScore.text = "Looking for characters"

        //despues de cargar datos
        pyObj = getPythonFile("setText")
        binding.tvScore.text = pyObj.callAttr("main",points, round).toString()
        binding.GroupA.visibility = View.VISIBLE
        binding.GroupB.visibility = View.VISIBLE
        binding.GroupC.visibility = View.VISIBLE
    }
    //Comprueba la palabra escrita, suma una ronda y asigna texto de juego
    private fun nextRound(){
        buildWord()
        foundWord()
        round++
        pyObj = getPythonFile("setText")
        binding.tvScore.text = pyObj.callAttr("main",points, round).toString()
    }

}