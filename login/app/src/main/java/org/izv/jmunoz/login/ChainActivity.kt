package org.izv.jmunoz.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.concurrent.thread
import kotlin.random.Random

class ChainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
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
    lateinit var score: TextView
    lateinit var etChain: EditText
    lateinit var btNext: Button
    lateinit var  word: String
    lateinit var DBword: String
    lateinit var  answer: String
    lateinit var aux: String
    lateinit var text: String
    var round = 1
    var points = 0
    var size = 834


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chain)

        init()

        btNext.setOnClickListener {
            if(round < 5){
                comprobation(it)
            }
            else{
                startActivity(Intent(this,EndActivity::class.java))
            }
        }

    }

    private fun init(){

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
        score = findViewById(R.id.tvScore)
        btNext = findViewById(R.id.btNextChain)
        etChain = findViewById(R.id.etWordChain)
        score.text = "Round "+round+"    "+points+" Points"
        word = ""
        //LEER DATOS
        var id = Random.nextInt(0, size - 1)
        db.collection("Palabras").document(id.toString()).get()
            .addOnSuccessListener{
                word = it.get("nombre").toString()
                answer(0)
            }
    }

    private fun comprobation(view: View){
        if(etChain.text.length == 5){
            DBword = "null"
            text = etChain.text.toString().uppercase()
                for (i in 0..size) {
                    db.collection("Palabras").document(i.toString()).get().addOnSuccessListener {
                        aux = it.get("nombre").toString()
                        if (text == aux) {
                            DBword = text

                        }
                    }
                }
            found()
        }
        else{
            Snackbar.make(view, "WORD LENGTH 5", Snackbar.LENGTH_LONG).show()
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
            chainB1.text = etChain.text[0].toString()
            chainB2.text = etChain.text[1].toString()
            chainB3.text = etChain.text[2].toString()
            chainB4.text = etChain.text[3].toString()
            chainB5.text = etChain.text[4].toString()
            chainB1.setBackgroundColor(Color.MAGENTA)
            chainB2.setBackgroundColor(Color.MAGENTA)
            chainB3.setBackgroundColor(Color.RED)
            chainB4.setBackgroundColor(Color.RED)
            chainB5.setBackgroundColor(Color.RED)
        }
        etChain.setText("")
    }

    private fun found(){
        if (DBword != "null") {
            answer(1)
            points++
        } else {
            answer(2)
            points--
        }
        round++
        score.text = "Round " + round + "    " + points + " Points"
    }

}