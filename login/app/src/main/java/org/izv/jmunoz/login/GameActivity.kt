package org.izv.jmunoz.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

class GameActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var lista: List<String>
    lateinit var  palabra: Palabra

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
    lateinit var btSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        init()

    }

    private fun init(){

        tvUser =findViewById(R.id.tvUser)
        wordTry =findViewById(R.id.etWord)
        btSend = findViewById(R.id.btSend)

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

        lista = mutableListOf()
        palabra = Palabra()
        var id = 0




    }


}