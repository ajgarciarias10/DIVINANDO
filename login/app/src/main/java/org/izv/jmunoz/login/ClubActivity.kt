package org.izv.jmunoz.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlin.random.Random

class ClubActivity : AppCompatActivity() {

    lateinit var iClub: ImageView
    lateinit var tvRound: TextView
    lateinit var tvClubName: TextView
    lateinit var btNext: Button
    lateinit var btTrue: Button
    lateinit var btFalse: Button
    lateinit var imageName: String
    lateinit var name: String
    lateinit var answer: String
    //data
    private val db = FirebaseFirestore.getInstance()
    lateinit var listado:  MutableList<DocumentSnapshot>
    lateinit var usados:  MutableList<Int>
    //Python
    lateinit var py: Python
    lateinit var pyObj: PyObject
    //Game var
    var round = 1
    var points = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club)

        init()

        btNext.setOnClickListener {
            isEnd(it)
        }

        btTrue.setOnClickListener {
                answer = R.string.true_.toString()
                answer(0)
        }
        btFalse.setOnClickListener {
                answer = R.string.false_.toString()
                answer(1)
        }

    }
    private fun answer(i: Int){
        //reset values
        btTrue.setBackgroundColor(Color.TRANSPARENT)
        btTrue.setTextColor(Color.GREEN)
        btFalse.setBackgroundColor(Color.TRANSPARENT)
        btFalse.setTextColor(Color.RED)

        //set values
        if(i == 0){
            btTrue.setBackgroundColor(Color.GREEN)
            btTrue.setTextColor(Color.WHITE)
        }
        else if(i == 1){
            btFalse.setBackgroundColor(Color.RED)
            btFalse.setTextColor(Color.WHITE)
        }
    }

    private fun comparation(){
        if(imageName == name && answer == R.string.true_.toString()){
            points++
        }
        else if(imageName != name && answer == R.string.false_.toString()){
            points++
        }
    }

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

    private fun getClub(){
        var num = Random.nextInt(0, 100)
        var id1 = RandomNum()
        var id2 = RandomNum()

        //Load True data
        if(num < 50 ) { //true
            Picasso.get().load(listado[id1].get("url").toString()).into(iClub)
            imageName = listado[id1].get("nombre").toString()
            name = imageName
            tvClubName.text = imageName
            usados.add(id1)
        }
        //Load False data
        else{
            Picasso.get().load(listado[id1].get("url").toString()).into(iClub)
            imageName = listado[id1].get("nombre").toString()
            name = listado[id2].get("nombre").toString()
            tvClubName.text = name
            usados.add(id1)
        }
    }

    private fun getPythonFile(name: String): PyObject{
        if (! Python.isStarted()) {
            Python.start( AndroidPlatform(this));

        }
        py = Python.getInstance()
        var pytobj = py.getModule(name) //give python script name
        return pytobj
    }

    private fun init(){
        //to assign
        iClub = findViewById(R.id.iClub)
        btTrue = findViewById(R.id.btTrue)
        btFalse = findViewById(R.id.btFalse)
        tvRound = findViewById(R.id.tvRound)
        tvClubName = findViewById(R.id.tvClubName)
        btNext = findViewById(R.id.btNextClub)
        listado = mutableListOf()
        usados = mutableListOf()

        //set values
        tvRound.text = "ROUND $round     $points Points"
        answer = ""
        btTrue.setBackgroundColor(Color.TRANSPARENT)
        btFalse.setBackgroundColor(Color.TRANSPARENT)

        //get club list
        db.collection("Clubs").get().addOnSuccessListener {
            listado = it.documents
            getClub()
        }
    }

    private fun isEnd(view: View){

        if(round < 5){
            if(answer == ""){
                Snackbar.make(view, "Select your answer before to next", Snackbar.LENGTH_LONG).setAction("hidden") {}.show()
            }
            else{
                comparation()
                nextRound()
            }

        }
        else{
            navigate()
        }
    }

    private fun nextRound(){
        round++
        answer = ""
        pyObj = getPythonFile("setText")
        tvRound.text = pyObj.callAttr("main",points, round).toString()
        answer(2)
        getClub()
    }

    private fun RandomNum(): Int{
        var numId = -1
        var found = 0

        pyObj = getPythonFile("validateNum") //python file name == "validateNum"
        numId = pyObj.callAttr("main",(listado.size- 1)).toInt() //python def name == "main"

        //mientras coincida numId con algun Id de la base datos
        while(found == 0) {
            numId = pyObj.callAttr("main", (listado.size - 1)).toInt()
            if (!foundIdDB(numId)) {
                found = 1
            }
        }
        return numId
    }

    private fun navigate(){
        var b = Bundle()
        var navigate = Intent(this,EndActivity::class.java)
        b.putString("game", "escudos")
        b.putString("points", points.toString())
        navigate.putExtras(b)
        startActivity(navigate)
        finish()
    }

}