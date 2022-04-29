package org.izv.jmunoz.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

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
    lateinit var score: TextView
    lateinit var etChain: EditText
    lateinit var btNext: Button
    var round = 1
    var points = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chain)

        init()

        btNext.setOnClickListener { comprobation() }

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

    }

    private fun comprobation(): Boolean{
        if(round >= 6){
            if(0 == 0){
                startActivity(Intent(this,EndActivity::class.java))
            }
            return true
        }
        else{
            round++
            score.text = "Round "+round+"    "+points+" Points"
            return false
        }
    }

}