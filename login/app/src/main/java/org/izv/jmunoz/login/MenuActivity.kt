package org.izv.jmunoz.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuActivity : AppCompatActivity() {

    lateinit var btWord: Button
    lateinit var btImage: Button
    lateinit var btBack: Button
    lateinit var btChains: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        init()

        btBack.setOnClickListener { startActivity(Intent(this,MainActivity::class.java)) }

        btWord.setOnClickListener { startActivity(Intent(this,GameActivity::class.java)) }

        btImage.setOnClickListener { startActivity(Intent(this,ClubActivity::class.java)) }

        btChains.setOnClickListener { startActivity(Intent(this,ChainActivity::class.java)) }

    }

    private fun init(){
        btWord = findViewById(R.id.btWords)
        btImage = findViewById(R.id.btImages)
        btBack = findViewById(R.id.btBackMenu)
        btChains = findViewById(R.id.btChains)
    }

}