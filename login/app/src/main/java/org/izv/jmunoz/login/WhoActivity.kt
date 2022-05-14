package org.izv.jmunoz.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlin.random.Random

class WhoActivity : AppCompatActivity() {

    lateinit var iWho: ImageView
    lateinit var iPeppo: ImageView
    lateinit var tvMsg: TextView
    lateinit var tvRound: TextView
    lateinit var btAnswerA:Button
    lateinit var btAnswerB:Button
    lateinit var btAnswerC:Button
    lateinit var btAnswerD:Button
    lateinit var card: CardView
    //Python
    lateinit var py: Python
    lateinit var pyObj: PyObject
    //data
    private val db = FirebaseFirestore.getInstance()
    lateinit var listado:  MutableList<DocumentSnapshot>
    lateinit var usados:  MutableList<Int>
    lateinit var answerList:  MutableList<Int>
    //Game var
    var id = -1
    var round = 1
    var load = 0
    var points = 0
    var answerPlayer = ""
    var realAnswer = -1
    var isVerified = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_who)

        init()

        //set answer if the game is started and isn't verified
        btAnswerA.setOnClickListener { if(!isVerified && load >= 8){answerPlayer = "A"; chooseAnswer("A")} }
        btAnswerB.setOnClickListener { if(!isVerified && load >= 8){answerPlayer = "B"; chooseAnswer("B")} }
        btAnswerC.setOnClickListener { if(!isVerified && load >= 8){answerPlayer = "C"; chooseAnswer("C")} }
        btAnswerD.setOnClickListener { if(!isVerified && load >= 8){answerPlayer = "D"; chooseAnswer("D")} }
        //to advance game
        card.setOnClickListener {
            startGame()
        }
    }

    private fun answerLoad(id: Int){
        //set who button is true answer
        realAnswer = Random.nextInt(1, 4)
        generateAnswerOptions(id, btAnswerA)
        generateAnswerOptions(id, btAnswerB)
        generateAnswerOptions(id, btAnswerC)
        generateAnswerOptions(id, btAnswerD)
        setTrueAnswer(realAnswer, id)
    }

    private fun answerVisibility(i: Int){
        if(i == 1){
            Picasso.get().load(R.drawable.peppo).into(iPeppo)
        }
        if(i == 0) {
            btAnswerA.visibility = View.INVISIBLE
            btAnswerB.visibility = View.INVISIBLE
            btAnswerC.visibility = View.INVISIBLE
            btAnswerD.visibility = View.INVISIBLE
        }
        else if(i == 2){
            btAnswerA.visibility = View.VISIBLE
            setCardPeppo(btAnswerA.text.toString(), R.drawable.peppo)
            btAnswerB.visibility = View.INVISIBLE
            btAnswerC.visibility = View.INVISIBLE
            btAnswerD.visibility = View.INVISIBLE
        }
        else if(i == 4){
            btAnswerA.visibility = View.VISIBLE
            btAnswerB.visibility = View.VISIBLE
            setCardPeppo(btAnswerB.text.toString(), R.drawable.peppo)
            btAnswerC.visibility = View.INVISIBLE
            btAnswerD.visibility = View.INVISIBLE
        }
        else if(i == 6){
            btAnswerA.visibility = View.VISIBLE
            btAnswerB.visibility = View.VISIBLE
            btAnswerC.visibility = View.VISIBLE
            setCardPeppo(btAnswerC.text.toString(), R.drawable.peppo)
            btAnswerD.visibility = View.INVISIBLE
        }
        else if(i == 8){
            btAnswerA.visibility = View.VISIBLE
            btAnswerB.visibility = View.VISIBLE
            btAnswerC.visibility = View.VISIBLE
            btAnswerD.visibility = View.VISIBLE
            setCardPeppo(btAnswerD.text.toString(), R.drawable.peppo)
        }

    }

    private fun chooseAnswer(a: String){
        btAnswerA.setBackgroundColor(Color.BLUE)
        btAnswerB.setBackgroundColor(Color.BLUE)
        btAnswerC.setBackgroundColor(Color.BLUE)
        btAnswerD.setBackgroundColor(Color.BLUE)
        Picasso.get().load(R.drawable.nerviouspeppo).into(iPeppo)
        pyObj = getPythonFile("setTextWho")
        if(a == "A"){
            btAnswerA.setBackgroundColor(Color.MAGENTA)
            tvMsg.text = pyObj.callAttr("main",-1).toString() +" "+
                    btAnswerA.text.toString().uppercase()
        }
        else if(a == "B"){
            btAnswerB.setBackgroundColor(Color.MAGENTA)
            tvMsg.text = pyObj.callAttr("main",-1).toString() +" "+
                    btAnswerB.text.toString().uppercase()
        }
        else if(a == "C"){
            btAnswerC.setBackgroundColor(Color.MAGENTA)
            tvMsg.text = pyObj.callAttr("main",-1).toString() +" "+
                    btAnswerC.text.toString().uppercase()
        }
        else if(a == "D"){
            btAnswerD.setBackgroundColor(Color.MAGENTA)
            tvMsg.text = pyObj.callAttr("main",-1).toString() +" "+
                    btAnswerD.text.toString().uppercase()
        }


    }

    private fun deleteAnswerList(){
        var i = 0
        while(i < answerList.size){
            answerList.removeAt(i)
        }
    }

    private fun generateAnswerOptions(num: Int, bt:Button){
        var id = Random.nextInt(0, listado.size - 1)
        var i = 0
        var verifiedDB = false
        var verifiedAnswer = false
        //verified number from database and answers options
        while(!verifiedDB || !verifiedAnswer) {
            //verified number from database
            while (num == id) {
                id = Random.nextInt(0, listado.size - 1)
                verifiedAnswer = false
            }
            verifiedDB = true
            if (answerList.size > 0) {
                //verified number from answers options
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
        //set answer for button and add id to answerList
        bt.text = listado[id].getString("nombre")
        answerList.add(id)
    }

    private fun generateNumValidate(): Int{
        var id = Random.nextInt(0, listado.size - 1)
        if(usados.size > 0){
            var i = 0
            while(i < usados.size){
                if(id == usados[i]){
                    id = Random.nextInt(0, listado.size - 1)
                    i = -1
                }
                i++
            }

        }
        return id
    }

    private fun getPerson(){
        id = generateNumValidate()
        answerLoad(id)
        Picasso.get().load(listado[id].get("url").toString()).into(iWho)
        usados.add(id)
    }

    private fun getPythonFile(name: String): PyObject {
        if (! Python.isStarted()) {
            Python.start( AndroidPlatform(this));
        }
        py = Python.getInstance()
        var pytobj = py.getModule(name) //give python script name
        return pytobj
    }

    private fun getTrueAnswer(){
        //player true answer
        if(validateAnswer() == 1){
            if (realAnswer == 1){
                btAnswerA.setBackgroundColor(Color.GREEN)
            }
            else if(realAnswer == 2){
                btAnswerB.setBackgroundColor(Color.GREEN)
            }
            else if(realAnswer == 3){
                btAnswerC.setBackgroundColor(Color.GREEN)
            }
            else if(realAnswer == 4){
                btAnswerD.setBackgroundColor(Color.GREEN)
            }
        }
        else{
            //True answer
            if (realAnswer == 1){
                btAnswerA.setBackgroundColor(Color.GREEN)
            }
            else if(realAnswer == 2){
                btAnswerB.setBackgroundColor(Color.GREEN)
            }
            else if(realAnswer == 3){
                btAnswerC.setBackgroundColor(Color.GREEN)
            }
            else if(realAnswer == 4){
                btAnswerD.setBackgroundColor(Color.GREEN)
            }
            //Player answer
            if(answerPlayer == "A"){
                btAnswerA.setBackgroundColor(Color.RED)
            }
            else if(answerPlayer == "B"){
                btAnswerB.setBackgroundColor(Color.RED)
            }
            else if(answerPlayer == "C"){
                btAnswerC.setBackgroundColor(Color.RED)
            }
            else if(answerPlayer == "D"){
                btAnswerD.setBackgroundColor(Color.RED)
            }
        }
    }

    private fun init(){

        instantiation()

        db.collection("Famosos").get().addOnSuccessListener {
            listado = it.documents
            getPerson()
            answerVisibility(load)
        }

        pyObj = getPythonFile("setTextWho")
        tvMsg.text = pyObj.callAttr("main",load).toString()
        tvRound.text = "Round $round   $points Points"

    }

    private fun instantiation(){
        iWho = findViewById(R.id.iWho)
        iPeppo = findViewById(R.id.iPeppo)
        tvMsg = findViewById(R.id.tvMsg)
        tvRound = findViewById(R.id.tvRoundWho)
        btAnswerA = findViewById(R.id.btAnswerA)
        btAnswerB = findViewById(R.id.btAnswerB)
        btAnswerC = findViewById(R.id.btAnswerC)
        btAnswerD = findViewById(R.id.btAnswerD)
        card = findViewById(R.id.cardPeppo)
        usados = mutableListOf()
        answerList = mutableListOf()
        listado = mutableListOf()
    }

    private fun isEnd(){
        if(answerPlayer == "" && round < 5){
            setCardPeppo("Select your answer before to next", R.drawable.peppo)
        }
        else {
            if (!isVerified) {
                validateAnswer()
                getTrueAnswer()
                isVerified = true
            }
            else {
                if(round < 5){
                    getPerson();
                    round++
                }
                else{
                    startActivity(Intent(this,EndActivity::class.java))
                }
                deleteAnswerList()
                tvRound.text = "ROUND $round  $points Points"
                answerPlayer = ""
                chooseAnswer(answerPlayer)
                isVerified = false
                setCardPeppo("", R.drawable.peppo)
            }
        }
    }

    private fun setCardPeppo(a: String, img: Int){
        tvMsg.text = a
        Picasso.get().load(img).into(iPeppo)
    }

    private fun setTrueAnswer(num: Int, id: Int){
        if(num == 1){
            btAnswerA.text = listado[id].getString("nombre")
        }
        else if(num == 2){
            btAnswerB.text = listado[id].getString("nombre")
        }
        else if(num == 3){
            btAnswerC.text = listado[id].getString("nombre")
        }
        else if(num == 4){
            btAnswerD.text = listado[id].getString("nombre")
        }
    }

    private fun startGame(){
        if(load < 8) {
            load++
            answerVisibility(load)
            tvMsg.text = pyObj.callAttr("main", load).toString()
        }
        else{
            isEnd()
        }
    }

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