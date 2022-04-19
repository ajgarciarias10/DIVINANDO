package org.izv.jmunoz.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.VERBOSE
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var lista: List<String>
    lateinit var  classPalabra: Palabra
    lateinit var  palabra: String
    var round: Int = 1

    private lateinit var tvUser: TextView
    lateinit var wordA1: TextView
    lateinit var wordA2: TextView
    lateinit var wordA3: TextView
    lateinit var wordA4: TextView
    lateinit var wordA5: TextView
    lateinit var wordB1: TextView
    lateinit var wordB2: TextView
    lateinit var wordB3: TextView
    lateinit var wordB4: TextView
    lateinit var wordB5: TextView
    lateinit var wordC1: TextView
    lateinit var wordC2: TextView
    lateinit var wordC3: TextView
    lateinit var wordC4: TextView
    lateinit var wordC5: TextView
    lateinit var wordD1: TextView
    lateinit var wordD2: TextView
    lateinit var wordD3: TextView
    lateinit var wordD4: TextView
    lateinit var wordD5: TextView
    lateinit var wordE1: TextView
    lateinit var wordE2: TextView
    lateinit var wordE3: TextView
    lateinit var wordE4: TextView
    lateinit var wordE5: TextView
    lateinit var wordTry: EditText
    lateinit var wordBox: TextInputLayout
    lateinit var btSend: Button
    lateinit var btBack: Button
    var points = 0
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        init()
        foundUser()

        btSend.setOnClickListener{
            game(palabra, round)
            round++
            setVisibility(round)
            wordTry.setText("")
        }
        btBack.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

    }

    private fun init(){

        tvUser =findViewById(R.id.tvUser)
        wordTry =findViewById(R.id.etWord)
        wordBox = findViewById(R.id.tvWord)
        btSend = findViewById(R.id.btSend)
        btBack = findViewById(R.id.btBackGame)

        wordA1 = findViewById(R.id.tvWordA1)
        wordA2 = findViewById(R.id.tvWordA2)
        wordA3 = findViewById(R.id.tvWordA3)
        wordA4 = findViewById(R.id.tvWordA4)
        wordA5 = findViewById(R.id.tvWordA5)

        wordB1 = findViewById(R.id.tvWordB1)
        wordB2 = findViewById(R.id.tvWordB2)
        wordB3 = findViewById(R.id.tvWordB3)
        wordB4 = findViewById(R.id.tvWordB4)
        wordB5 = findViewById(R.id.tvWordB5)

        wordC1 = findViewById(R.id.tvWordC1)
        wordC2 = findViewById(R.id.tvWordC2)
        wordC3 = findViewById(R.id.tvWordC3)
        wordC4 = findViewById(R.id.tvWordC4)
        wordC5 = findViewById(R.id.tvWordC5)

        wordD1 = findViewById(R.id.tvWordD1)
        wordD2 = findViewById(R.id.tvWordD2)
        wordD3 = findViewById(R.id.tvWordD3)
        wordD4 = findViewById(R.id.tvWordD4)
        wordD5 = findViewById(R.id.tvWordD5)

        wordE1 = findViewById(R.id.tvWordE1)
        wordE2 = findViewById(R.id.tvWordE2)
        wordE3 = findViewById(R.id.tvWordE3)
        wordE4 = findViewById(R.id.tvWordE4)
        wordE5 = findViewById(R.id.tvWordE5)

        round = 1
        setVisibility(round)

        lista = mutableListOf()
        classPalabra = Palabra()
        lista = classPalabra.getDB()
        palabra = "ABABA"
        /*var id = Random.nextInt(0, (lista.size-1))
        palabra = lista.get(id)
        wordTry.setText(palabra)
        //LEER DATOS
        db.collection("Palabras").document("ABABA").get()
        .addOnSuccessListener{
            wordTry.setText(id.toString() +" = "+it.get("id").toString()+" "+it.get("nombre") as String?)
        }*/


    }

    private fun foundChar(palabra: String, word: TextView, i: Int){

        if(palabra[i].uppercase() == wordTry.text[i].uppercase()){
            word.setBackgroundColor(Color.GREEN)
        }
        else{
            for(j in 0..4) {
                if(palabra[j].uppercase() == wordTry.text[i].uppercase()) {
                    word.setBackgroundColor(Color.YELLOW)
                    points ++
                }
            }
        }


    }

    private fun foundWord(palabra: String, round: Int){
        if(round == 1) {
            foundChar(palabra, wordA1, 0)
            foundChar(palabra, wordA2, 1)
            foundChar(palabra, wordA3, 2)
            foundChar(palabra, wordA4, 3)
            foundChar(palabra, wordA5, 4)
        }
        else if(round == 2){
            foundChar(palabra, wordB1, 0)
            foundChar(palabra, wordB2, 1)
            foundChar(palabra, wordB3, 2)
            foundChar(palabra, wordB4, 3)
            foundChar(palabra, wordB5, 4)
        }
        else if(round == 3){
            foundChar(palabra, wordC1, 0)
            foundChar(palabra, wordC2, 1)
            foundChar(palabra, wordC3, 2)
            foundChar(palabra, wordC4, 3)
            foundChar(palabra, wordC5, 4)
        }
        else if(round == 4){
            foundChar(palabra, wordD1, 0)
            foundChar(palabra, wordD2, 1)
            foundChar(palabra, wordD3, 2)
            foundChar(palabra, wordD4, 3)
            foundChar(palabra, wordD5, 4)
        }
        else if(round == 5){
            foundChar(palabra, wordE1, 0)
            foundChar(palabra, wordE2, 1)
            foundChar(palabra, wordE3, 2)
            foundChar(palabra, wordE4, 3)
            foundChar(palabra, wordE5, 4)
        }
    }

    private fun theWord(round: Int){
        if(round == 1) {
            wordA1.setBackgroundColor(Color.GREEN)
            wordA2.setBackgroundColor(Color.GREEN)
            wordA3.setBackgroundColor(Color.GREEN)
            wordA4.setBackgroundColor(Color.GREEN)
            wordA5.setBackgroundColor(Color.GREEN)
            points +=25
        }
        else if(round == 2) {
            wordB1.setBackgroundColor(Color.GREEN)
            wordB2.setBackgroundColor(Color.GREEN)
            wordB3.setBackgroundColor(Color.GREEN)
            wordB4.setBackgroundColor(Color.GREEN)
            wordB5.setBackgroundColor(Color.GREEN)
            points +=20
        }
        else if(round == 3) {
            wordC1.setBackgroundColor(Color.GREEN)
            wordC2.setBackgroundColor(Color.GREEN)
            wordC3.setBackgroundColor(Color.GREEN)
            wordC4.setBackgroundColor(Color.GREEN)
            wordC5.setBackgroundColor(Color.GREEN)
            points +=15
        }
        else if(round == 4) {
            wordD1.setBackgroundColor(Color.GREEN)
            wordD2.setBackgroundColor(Color.GREEN)
            wordD3.setBackgroundColor(Color.GREEN)
            wordD4.setBackgroundColor(Color.GREEN)
            wordD5.setBackgroundColor(Color.GREEN)
            points +=10
        }
        else if(round == 5) {
            wordE1.setBackgroundColor(Color.GREEN)
            wordE2.setBackgroundColor(Color.GREEN)
            wordE3.setBackgroundColor(Color.GREEN)
            wordE4.setBackgroundColor(Color.GREEN)
            wordE5.setBackgroundColor(Color.GREEN)
            points +=5
        }
    }

    private fun setVisibility(round: Int){
        if(round == 1){
            wordB1.visibility = View.INVISIBLE
            wordB2.visibility = View.INVISIBLE
            wordB3.visibility = View.INVISIBLE
            wordB4.visibility = View.INVISIBLE
            wordB5.visibility = View.INVISIBLE

            wordC1.visibility = View.INVISIBLE
            wordC2.visibility = View.INVISIBLE
            wordC3.visibility = View.INVISIBLE
            wordC4.visibility = View.INVISIBLE
            wordC5.visibility = View.INVISIBLE

            wordD1.visibility = View.INVISIBLE
            wordD2.visibility = View.INVISIBLE
            wordD3.visibility = View.INVISIBLE
            wordD4.visibility = View.INVISIBLE
            wordD5.visibility = View.INVISIBLE

            wordE1.visibility = View.INVISIBLE
            wordE2.visibility = View.INVISIBLE
            wordE3.visibility = View.INVISIBLE
            wordE4.visibility = View.INVISIBLE
            wordE5.visibility = View.INVISIBLE
        }
        else if(round == 2){
            //btBack.visibility = View.INVISIBLE
            wordB1.visibility = View.VISIBLE
            wordB2.visibility = View.VISIBLE
            wordB3.visibility = View.VISIBLE
            wordB4.visibility = View.VISIBLE
            wordB5.visibility = View.VISIBLE
        }
        else if(round == 3){
            wordC1.visibility = View.VISIBLE
            wordC2.visibility = View.VISIBLE
            wordC3.visibility = View.VISIBLE
            wordC4.visibility = View.VISIBLE
            wordC5.visibility = View.VISIBLE
        }
        else if(round == 4){
            wordD1.visibility = View.VISIBLE
            wordD2.visibility = View.VISIBLE
            wordD3.visibility = View.VISIBLE
            wordD4.visibility = View.VISIBLE
            wordD5.visibility = View.VISIBLE
        }
        else if(round == 5){
            wordE1.visibility = View.VISIBLE
            wordE2.visibility = View.VISIBLE
            wordE3.visibility = View.VISIBLE
            wordE4.visibility = View.VISIBLE
            wordE5.visibility = View.VISIBLE
        }
    }

    private fun putWord(round: Int){
        if(round == 1){
            wordA1.text = wordTry.text[0].toString()
            wordA2.text = wordTry.text[1].toString()
            wordA3.text = wordTry.text[2].toString()
            wordA4.text = wordTry.text[3].toString()
            wordA5.text = wordTry.text[4].toString()
        }
        else if(round == 2){
            wordB1.text = wordTry.text[0].toString()
            wordB2.text = wordTry.text[1].toString()
            wordB3.text = wordTry.text[2].toString()
            wordB4.text = wordTry.text[3].toString()
            wordB5.text = wordTry.text[4].toString()
        }
        else if(round == 3){
            wordC1.text = wordTry.text[0].toString()
            wordC2.text = wordTry.text[1].toString()
            wordC3.text = wordTry.text[2].toString()
            wordC4.text = wordTry.text[3].toString()
            wordC5.text = wordTry.text[4].toString()
        }
        else if(round == 4){
            wordD1.text = wordTry.text[0].toString()
            wordD2.text = wordTry.text[1].toString()
            wordD3.text = wordTry.text[2].toString()
            wordD4.text = wordTry.text[3].toString()
            wordD5.text = wordTry.text[4].toString()
        }
        else if(round == 5){
            wordE1.text = wordTry.text[0].toString()
            wordE2.text = wordTry.text[1].toString()
            wordE3.text = wordTry.text[2].toString()
            wordE4.text = wordTry.text[3].toString()
            wordE5.text = wordTry.text[4].toString()
        }
    }

    private fun game(palabra: String, round: Int){
        if(round < 6){
            if(palabra.uppercase() == wordTry.text.toString().uppercase()) {
                theWord(round)
                putWord(round)
                this.round = 6
                wordBox.visibility = View.INVISIBLE
                savePoints()
                btSend.text = points.toString()
            }
            else{
                foundWord(palabra, round)
                putWord(round)
            }
        }
        else{
            startActivity(Intent(this,EndActivity::class.java))
            finish()
        }
    }

    private fun foundUser(){
        var i = 2
        for(j in 0..i){
            db.collection("Usuarios").document(j.toString()).get()
                .addOnSuccessListener{
                    if(it.get("mail").toString() == MainActivity.user.mail){
                        tvUser.text = it.get("nombre").toString()
                        id = j.toString()
                    }

                }
        }
    }

    private fun savePoints(){
        var mail: String = ""
        var nombre: String = ""
        var point: Int = 0
        db.collection("Usuarios").document(id).get()
            .addOnSuccessListener{
                mail= it.get("mail").toString()
                nombre = it.get("nombre").toString()
                point = it.get("points").toString() as Int
        }

        if(point > points){
            points = point
        }

        db.collection("Usuarios").document(id).set(
            hashMapOf("mail" to mail,"nombre" to nombre,"points" to points)
        )
    }

}