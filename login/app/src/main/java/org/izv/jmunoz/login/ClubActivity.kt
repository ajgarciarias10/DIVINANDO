package org.izv.jmunoz.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlin.random.Random

class ClubActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    lateinit var iClub: ImageView
    lateinit var tvRound: TextView
    lateinit var tvClubName: TextView
    lateinit var btNext: Button
    lateinit var btTrue: Button
    lateinit var btFalse: Button
    lateinit var imageName: String
    lateinit var name: String
    var size = 8
    var round = 1
    var points = 0
    lateinit var  answer: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club)

        init()

        btNext.setOnClickListener {
            if(round < 5){
                comparation()
            }
            else{
                startActivity(Intent(this,EndActivity::class.java))
            }
        }

        btTrue.setOnClickListener {
                answer = "true"
                answer(0)
        }
        btFalse.setOnClickListener {
                answer = "false"
                answer(1)
        }

    }

    private fun comparation(){
        if(imageName == name && answer == "true"){
            points++
        }
        else if(imageName != name && answer == "false"){
            points++
        }
        round++
        tvRound.text = "ROUND "+round+"     "+points+" Points"
        answer(2)
        getClub()
    }

    private fun answer(i: Int){
        btTrue.setBackgroundColor(Color.TRANSPARENT)
        btTrue.setTextColor(Color.GREEN)
        btFalse.setBackgroundColor(Color.TRANSPARENT)
        btFalse.setTextColor(Color.RED)
        if(i == 0){
            btTrue.setBackgroundColor(Color.GREEN)
            btTrue.setTextColor(Color.WHITE)
        }
        else if(i == 1){
            btFalse.setBackgroundColor(Color.RED)
            btFalse.setTextColor(Color.WHITE)
        }
    }

    private fun init(){
        iClub = findViewById(R.id.iClub)
        btTrue = findViewById(R.id.btTrue)
        btFalse = findViewById(R.id.btFalse)
        tvRound = findViewById(R.id.tvRound)
        tvClubName = findViewById(R.id.tvClubName)
        btNext = findViewById(R.id.btNextClub)
        tvRound.text = "ROUND "+round+"     "+points+" Points"
        btTrue.setBackgroundColor(Color.TRANSPARENT)
        btFalse.setBackgroundColor(Color.TRANSPARENT)
        getClub()
    }

    private fun getClub(){
        var clubID = Random.nextInt(0, size - 1)
        var clubID2 = Random.nextInt(0, size - 1)
        var num = Random.nextInt(0, 100)

        //LEER DATOS
        if(num < 50 ) { //true
            db.collection("Clubs").document(clubID.toString()).get()
                .addOnSuccessListener {
                    imageName = it.get("nombre").toString()
                    tvClubName.text = imageName
                    name = imageName
                    Picasso.get().load(it.get("url").toString()).into(iClub)
                }
        }
        else{ //false
            db.collection("Clubs").document(clubID.toString()).get()
                .addOnSuccessListener {
                    imageName = it.get("nombre").toString()
                    Picasso.get().load(it.get("url").toString()).into(iClub)
                }
            db.collection("Clubs").document(clubID2.toString()).get()
                .addOnSuccessListener {
                    name = it.get("nombre").toString()
                    tvClubName.text = name
                }
        }
    }

}