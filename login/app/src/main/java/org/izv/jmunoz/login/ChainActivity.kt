package org.izv.jmunoz.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class ChainActivity : AppCompatActivity() {

    lateinit var chainA1: TextView
    lateinit var chainA2: TextView
    lateinit var chainA3: TextView
    lateinit var chainA4: TextView
    lateinit var chainA5: TextView

    lateinit var chainB1: TextView
    lateinit var chainB2: TextView
    lateinit var chainB3: TextView
    lateinit var chainB4: TextView
    lateinit var chainB5: TextView

    lateinit var chainC1: TextView
    lateinit var chainC2: TextView
    lateinit var chainC3: TextView
    lateinit var chainC4: TextView
    lateinit var chainC5: TextView

    lateinit var groupA: Group
    lateinit var groupB: Group
    lateinit var groupC: Group

    lateinit var score: TextView
    lateinit var btNext: Button
    //word
    lateinit var word: String
    lateinit var DBword: String
    lateinit var answerText: String

    //Python
    lateinit var py: Python
    lateinit var pyObj: PyObject
    //data
    private val db = FirebaseFirestore.getInstance()
    lateinit var listado:  MutableList<DocumentSnapshot>
    var idWord = -1
    //Game var
    var round = 1
    var points = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chain)

        init()

        btNext.setOnClickListener {
            isEnd(it)
        }

    }
    private fun answer(i: Int){
        if(i == 0){
            chainA1.text = word[0].toString()
            chainA2.text = word[1].toString()
            chainA3.text = "_"
            chainA4.text = "_"
            chainA5.text = "_"
            chainA1.setBackgroundColor(Color.GREEN)
            chainA2.setBackgroundColor(Color.GREEN)
        }
        else if(i == 1){
            chainB1.text = DBword[0].toString()
            chainB2.text = DBword[1].toString()
            chainB3.text = DBword[2].toString()
            chainB4.text = DBword[3].toString()
            chainB5.text = DBword[4].toString()
            chainB1.setBackgroundColor(Color.GREEN)
            chainB2.setBackgroundColor(Color.GREEN)
            chainB3.setBackgroundColor(Color.GREEN)
            chainB4.setBackgroundColor(Color.GREEN)
            chainB5.setBackgroundColor(Color.GREEN)
            chainA1.text = DBword[3].toString()
            chainA2.text = DBword[4].toString()
        }
        else if(i == 2){
            chainB1.text = chainC1.text
            chainB2.text = chainC2.text
            chainB3.text = chainC3.text
            chainB4.text = chainC4.text
            chainB5.text = chainC5.text
            chainB1.setBackgroundColor(Color.MAGENTA)
            chainB2.setBackgroundColor(Color.MAGENTA)
            chainB3.setBackgroundColor(Color.RED)
            chainB4.setBackgroundColor(Color.RED)
            chainB5.setBackgroundColor(Color.RED)
        }
        chainC1.text = ""
        chainC2.text = ""
        chainC3.text = ""
        chainC4.text = ""
        chainC5.text = ""
    }
    private fun buildWord(){
        answerText = ""+chainC1.text+chainC2.text+chainC3.text+chainC4.text+chainC5.text
        answerText = answerText.uppercase()
    }
    private fun comprobation(view: View){
        if(isEmptyChar()) {
            Snackbar.make(view, "Error  5 length word please.", Snackbar.LENGTH_LONG).setAction("hidden") {}.show()
        }
        else{
            nextRound()
        }
    }
    private fun foundWord(){
        DBword = ""
        var i = 0
        //existe la palabra en DB?
        while (i < listado.size) {
            if (listado[i].get("nombre") == answerText) {
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
    private fun getPythonFile(name: String): PyObject {
        if (! Python.isStarted()) {
            Python.start( AndroidPlatform(this));
        }
        py = Python.getInstance()
        var pytobj = py.getModule(name) //give python script name
        return pytobj
    }
    private fun init(){
        instanciation()
        //Load data
        db.collection("Palabras").get().addOnSuccessListener {
            listado = it.documents
            idWord = Random.nextInt(0, listado.size - 1)
            word = listado[idWord].get("nombre").toString()
            answer(0)
        }

        loadScreen()


    }
    private fun instanciation(){
        chainA1 = findViewById(R.id.tvChainA1)
        chainA2 = findViewById(R.id.tvChainA2)
        chainA3 = findViewById(R.id.tvChainA3)
        chainA4 = findViewById(R.id.tvChainA4)
        chainA5 = findViewById(R.id.tvChainA5)

        chainB1 = findViewById(R.id.tvChainB1)
        chainB2 = findViewById(R.id.tvChainB2)
        chainB3 = findViewById(R.id.tvChainB3)
        chainB4 = findViewById(R.id.tvChainB4)
        chainB5 = findViewById(R.id.tvChainB5)

        chainC1 = findViewById(R.id.tvChainC1)
        chainC2 = findViewById(R.id.tvChainC2)
        chainC3 = findViewById(R.id.tvChainC3)
        chainC4 = findViewById(R.id.tvChainC4)
        chainC5 = findViewById(R.id.tvChainC5)

        groupA = findViewById(R.id.GroupA)
        groupB = findViewById(R.id.GroupB)
        groupC = findViewById(R.id.GroupC)

        score = findViewById(R.id.tvScore)
        btNext = findViewById(R.id.btNextChain)

        listado = mutableListOf()
        word = ""
    }
    private fun isEmptyChar(): Boolean{
        if(chainC1.length() != 1 || chainC2.length() != 1 || chainC3.length() != 1 ||
            chainC4.length() != 1 || chainC5.length() != 1) {
            return true
        }
        return false
    }
    private fun isEnd(view: View){
        if(round < 5){
            comprobation(view)
        }
        else{
            startActivity(Intent(this,EndActivity::class.java))
        }
    }
    private fun loadScreen(){
        //before load data
        groupA.visibility = View.INVISIBLE
        groupB.visibility = View.INVISIBLE
        groupC.visibility = View.INVISIBLE
        score.text = "Looking for characters"

        Thread.sleep(5000)

        //after load data
        pyObj = getPythonFile("setText")
        score.text = pyObj.callAttr("main",points, round).toString()
        groupA.visibility = View.VISIBLE
        groupB.visibility = View.VISIBLE
        groupC.visibility = View.VISIBLE
    }
    private fun nextRound(){
        buildWord()
        foundWord()
        round++
        pyObj = getPythonFile("setText")
        score.text = pyObj.callAttr("main",points, round).toString()
    }
}