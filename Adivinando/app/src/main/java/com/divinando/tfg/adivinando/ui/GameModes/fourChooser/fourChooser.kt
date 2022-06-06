package com.divinando.tfg.adivinando.ui.GameModes.fourChooser

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.divinando.tfg.adivinando.R
import com.divinando.tfg.adivinando.databinding.FragmentEncadenadosBinding
import com.divinando.tfg.adivinando.databinding.FragmentFourChooserBinding
import com.divinando.tfg.adivinando.model.entity.GameObjeto
import com.divinando.tfg.adivinando.ui.MainActivity
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlin.random.Random

class fourChooser : Fragment() {

    private var _binding: FragmentFourChooserBinding? = null
    private val binding get() = _binding!!

    //Python
    lateinit var py: Python
    lateinit var pyObj: PyObject
    //data
    lateinit var listado:  MutableList<DocumentSnapshot>
    lateinit var usados:  MutableList<Int>
    lateinit var answerList:  MutableList<Int>
    var bundle = Bundle()
    //Objeto del juego
    lateinit var objeto: GameObjeto
    //variables del juego
    var userID = -1
    var round = 1
    var load = 0
    var points = 0
    var answerPlayer = ""
    var realAnswer = -1
    var isVerified = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFourChooserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        //Asignar respuesta si el juego se cargo y la respuesta no esta verificada
        binding.btAnswerA.setOnClickListener { if(!isVerified && load >= 8){answerPlayer = "A"; chooseAnswer("A")} }
        binding.btAnswerB.setOnClickListener { if(!isVerified && load >= 8){answerPlayer = "B"; chooseAnswer("B")} }
        binding.btAnswerC.setOnClickListener { if(!isVerified && load >= 8){answerPlayer = "C"; chooseAnswer("C")} }
        binding.btAnswerD.setOnClickListener { if(!isVerified && load >= 8){answerPlayer = "D"; chooseAnswer("D")} }
        //Avanzar en el juego
        binding.cardPeppo.setOnClickListener {
            startGame()
        }

    }
    //Carga cada respuesta y asigna al azar el lugar de la respuesta correcta
    private fun answerLoad(id: Int){
        realAnswer = Random.nextInt(1, 4)
        generateAnswerOptions(id, binding.btAnswerA)
        generateAnswerOptions(id, binding.btAnswerB)
        generateAnswerOptions(id, binding.btAnswerC)
        generateAnswerOptions(id, binding.btAnswerD)
        setTrueAnswer(realAnswer, id)
    }
    //Visibiliza al inicio del juego  las respuestas 1 a 1
    private fun answerVisibility(i: Int){
        if(i == 1){
            Picasso.get().load(R.drawable.peppo).into(binding.iPeppo)
        }
        if(i == 0) {
            binding.btAnswerA.visibility = View.INVISIBLE
            binding.btAnswerB.visibility = View.INVISIBLE
            binding.btAnswerC.visibility = View.INVISIBLE
            binding.btAnswerD.visibility = View.INVISIBLE
        }
        else if(i == 2){
            binding.btAnswerA.visibility = View.VISIBLE
            setCardPeppo(binding.btAnswerA.text.toString(), R.drawable.peppo)
            binding.btAnswerB.visibility = View.INVISIBLE
            binding.btAnswerC.visibility = View.INVISIBLE
            binding.btAnswerD.visibility = View.INVISIBLE
        }
        else if(i == 4){
            binding.btAnswerA.visibility = View.VISIBLE
            binding.btAnswerB.visibility = View.VISIBLE
            setCardPeppo(binding.btAnswerB.text.toString(), R.drawable.peppo)
            binding.btAnswerC.visibility = View.INVISIBLE
            binding.btAnswerD.visibility = View.INVISIBLE
        }
        else if(i == 6){
            binding.btAnswerA.visibility = View.VISIBLE
            binding.btAnswerB.visibility = View.VISIBLE
            binding.btAnswerC.visibility = View.VISIBLE
            setCardPeppo(binding.btAnswerC.text.toString(), R.drawable.peppo)
            binding.btAnswerD.visibility = View.INVISIBLE
        }
        else if(i == 8){
            binding.btAnswerA.visibility = View.VISIBLE
            binding.btAnswerB.visibility = View.VISIBLE
            binding.btAnswerC.visibility = View.VISIBLE
            binding.btAnswerD.visibility = View.VISIBLE
            setCardPeppo(binding.btAnswerD.text.toString(), R.drawable.peppo)
        }

    }
    //Selecciona el texto del ayudante (Peppo)
    private fun chooseAnswer(a: String){
        binding.btAnswerA.setBackgroundColor(Color.parseColor("#667EEA"))
        binding.btAnswerB.setBackgroundColor(Color.parseColor("#667EEA"))
        binding.btAnswerC.setBackgroundColor(Color.parseColor("#667EEA"))
        binding.btAnswerD.setBackgroundColor(Color.parseColor("#667EEA"))
        Picasso.get().load(R.drawable.nerviouspeppo).into(binding.iPeppo)
        //Llama al archivo python que devuelve el texto para el ayudante
        pyObj = getPythonFile("setTextWho")
        if(a == "A"){
            binding.btAnswerA.setBackgroundColor(Color.MAGENTA)
            binding.tvMsg.text = pyObj.callAttr("main",-1).toString() +" "+
                    binding.btAnswerA.text.toString().uppercase()
        }
        else if(a == "B"){
            binding.btAnswerB.setBackgroundColor(Color.MAGENTA)
            binding.tvMsg.text = pyObj.callAttr("main",-1).toString() +" "+
                    binding.btAnswerB.text.toString().uppercase()
        }
        else if(a == "C"){
            binding.btAnswerC.setBackgroundColor(Color.MAGENTA)
            binding.tvMsg.text = pyObj.callAttr("main",-1).toString() +" "+
                    binding.btAnswerC.text.toString().uppercase()
        }
        else if(a == "D"){
            binding.btAnswerD.setBackgroundColor(Color.MAGENTA)
            binding.tvMsg.text = pyObj.callAttr("main",-1).toString() +" "+
                    binding.btAnswerD.text.toString().uppercase()
        }


    }
    //Elimina llos textos de las respuestas
    private fun deleteAnswerList(){
        var i = 0
        while(i < answerList.size){
            answerList.removeAt(i)
        }
    }
    //Verifica que las 4 respuestas no coincida con la respuesta correcta, ni entre ellas
    private fun generateAnswerOptions(num: Int, bt: Button){
        var id = Random.nextInt(0, listado.size - 1)
        var i = 0
        var verifiedDB = false
        var verifiedAnswer = false
        //Mientras que se repita una respuesta
        while(!verifiedDB || !verifiedAnswer) {
            //Comprueba si la respuesta coincide con la respuesta correcta
            while (num == id) {
                id = Random.nextInt(0, listado.size - 1)
                verifiedAnswer = false
            }
            verifiedDB = true
            if (answerList.size > 0) {
                //Comprueba si la respuesta ya fue asignada anteriormente
                while (i < answerList.size) {
                    if (id == answerList[i]) {
                        id = Random.nextInt(0, listado.size - 1)
                        i = -1
                        verifiedDB = false
                    }
                    i++
                }
            }
            verifiedAnswer = true
        }
        //Asigna respuesta y la añade a la lista de respuestas para que no se repitan
        bt.text = listado[id].getString("nombre")
        answerList.add(id)
    }
    //Devuelve una id (respuesta) que no este en uso
    private fun generateNumValidate(): Int{
        var id = Random.nextInt(0, objeto.listaUrl!!.size - 1)
        //si hay lista de respuestas usadas
        if(usados.size > 0){
            var i = 0
            while(i < usados.size){
                if(id == usados[i]){
                    id = Random.nextInt(0, objeto.listaUrl!!.size - 1)
                    i = -1
                }
                i++
            }

        }
        return id
    }
    //Obtiene un famoso de la lista (id, respuesta, imagen, lista de usados)
    private fun getPerson(){
        userID = generateNumValidate()
        answerLoad(userID)
        Picasso.get().load(listado[userID].get("url").toString()).into(binding.iWho)
        usados.add(userID)
    }
    //Metodo que carga archivos python en un objeto python que devuelve
    private fun getPythonFile(name: String): PyObject {
        if (! Python.isStarted()) {
            Python.start( AndroidPlatform(requireContext()));
        }
        py = Python.getInstance()
        var pytobj = py.getModule(name) //give python script name
        return pytobj
    }
    //Obtiene resultado de la respuesta elegida
    private fun getTrueAnswer(){
        //Respuesta acertada
        if(validateAnswer() == 1){
            if (realAnswer == 1){
                binding.btAnswerA.setBackgroundColor(Color.GREEN)
            }
            else if(realAnswer == 2){
                binding.btAnswerB.setBackgroundColor(Color.GREEN)
            }
            else if(realAnswer == 3){
                binding.btAnswerC.setBackgroundColor(Color.GREEN)
            }
            else if(realAnswer == 4){
                binding.btAnswerD.setBackgroundColor(Color.GREEN)
            }
        }
        //Respuesta erronea
        else{
            //Respuesta correcta en verde
            if (realAnswer == 1){
                binding.btAnswerA.setBackgroundColor(Color.GREEN)
            }
            else if(realAnswer == 2){
                binding.btAnswerB.setBackgroundColor(Color.GREEN)
            }
            else if(realAnswer == 3){
                binding.btAnswerC.setBackgroundColor(Color.GREEN)
            }
            else if(realAnswer == 4){
                binding.btAnswerD.setBackgroundColor(Color.GREEN)
            }
            //Respuesta elegida en rojo
            if(answerPlayer == "A"){
                binding.btAnswerA.setBackgroundColor(Color.RED)
            }
            else if(answerPlayer == "B"){
                binding.btAnswerB.setBackgroundColor(Color.RED)
            }
            else if(answerPlayer == "C"){
                binding.btAnswerC.setBackgroundColor(Color.RED)
            }
            else if(answerPlayer == "D"){
                binding.btAnswerD.setBackgroundColor(Color.RED)
            }
        }
    }

    private fun init(){

        usados = mutableListOf()
        answerList = mutableListOf()
        listado = mutableListOf()

        bundle = requireArguments()
        //Sacamos el objeto que nos pasa en este caso es del modo de juego  normal
        objeto = bundle.getSerializable("juegos") as GameObjeto
        listado = objeto.listaUrl!!
        //obtiene famoso
        getPerson()
        //Carga respuestas 1 a 1
        answerVisibility(load)
        //Obtiene texto del ayudante y asigna texto del juego
        pyObj = getPythonFile("setTextWho")
        binding.tvMsg.text = pyObj.callAttr("main",load).toString()
        binding.tvRoundWho.text = "Round $round   $points Points"

    }
    //Avanzar de ronda
    private fun isEnd(){
        //Comprueba respuesta vacia
        if(answerPlayer == "" && round < 5){
            setCardPeppo("Select your answer before to next", R.drawable.peppo)
        }
        else {
            //No esta comprobada la respuesta, la comprueba
            if (!isVerified) {
                getTrueAnswer()
                isVerified = true
            }
            //si esta comprobada la respuesta
            else {
                if(round < 5){
                    //Obtiene famoso, borra y reasigna respuestas, pasa de ronda y asigna texto del juego
                    getPerson();
                    deleteAnswerList()
                    answerPlayer = ""
                    chooseAnswer(answerPlayer)
                    isVerified = false
                    setCardPeppo("", R.drawable.peppo)
                    round++
                    binding.tvRoundWho.text = "ROUND $round  $points Points"
                }
                else if(round == 5){
                    //resultado final del juego
                    binding.btAnswerA.visibility = View.INVISIBLE
                    binding.btAnswerB.visibility = View.INVISIBLE
                    binding.btAnswerC.visibility = View.INVISIBLE
                    binding.btAnswerD.visibility = View.INVISIBLE
                    binding.tvRoundWho.text = "ROUND $round  $points Points"
                    round++
                }
                else{
                    //asigna juego y puntuacion al objeto del juego y viaja al siguiente fragment
                    MainActivity.ObjUser.game = "famosos"
                    MainActivity.ObjUser.point = points.toString()
                    findNavController().navigate(R.id.chooser_toend)
                }
            }
        }
    }
    //Asigna texto e imagen al ayudante
    private fun setCardPeppo(a: String, img: Int){
        binding.tvMsg.text = a
        Picasso.get().load(img).into(binding.iPeppo)
    }
    //Asigna el lugar de la respuesta correcta
    private fun setTrueAnswer(num: Int, id: Int){
        if(num == 1){
            binding.btAnswerA.text = listado[id].getString("nombre")
        }
        else if(num == 2){
            binding.btAnswerB.text = listado[id].getString("nombre")
        }
        else if(num == 3){
            binding.btAnswerC.text = listado[id].getString("nombre")
        }
        else if(num == 4){
            binding.btAnswerD.text = listado[id].getString("nombre")
        }
    }
    //Carga al inicio las respuestas 1 a 1
    private fun startGame(){
        if(load < 8) {
            load++
            answerVisibility(load)
            binding.tvMsg.text = pyObj.callAttr("main", load).toString()
        }
        else{
            isEnd()
        }
    }
    //Comprueba si se acerto en la respuesta
    private fun validateAnswer(): Int{
        var i = 0
        if(     answerPlayer == "A" && realAnswer == 1){ i = 1 }
        else if(answerPlayer == "B" && realAnswer == 2){ i = 1 }
        else if(answerPlayer == "C" && realAnswer == 3){ i = 1 }
        else if(answerPlayer == "D" && realAnswer == 4){ i = 1 }

        if(i == 1){
            points++
            setCardPeppo("you get 1 point", R.drawable.happypeppo)
        }
        else{
            setCardPeppo("bad lucky don't worry", R.drawable.sadpeppo)
        }
        return i
    }

}