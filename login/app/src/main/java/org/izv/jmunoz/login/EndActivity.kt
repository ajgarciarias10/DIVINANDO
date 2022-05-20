package org.izv.jmunoz.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class EndActivity : AppCompatActivity() {

    lateinit var tvrecord: TextView
    lateinit var tvPoints: TextView
    lateinit var btNext: Button
    lateinit var user: String
    lateinit var game: String
    lateinit var points:String
    var id = 0
    var totalPoints = "0"
    private val db = FirebaseFirestore.getInstance()
    private val db2 = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        init()

        btNext.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }

    }

    private fun init(){
        tvrecord = findViewById(R.id.tvPlayerRecord)
        tvPoints = findViewById(R.id.tvPlayerPoint)
        btNext = findViewById(R.id.btNextEnd)
        user = "Sonia" // al logear guardar el campo nombre de la base datos en MainActivity y que se acceda desde cualquier pantalla
        game = intent.extras?.get("game").toString()
        points = intent.extras?.get("points").toString()
        //get record
        db.collection("Usuarios").get().addOnSuccessListener {
            var i = 0
            var size = it.documents.size
            while(i in 0..size){
                db.collection("Usuarios").document(i.toString()).get().addOnSuccessListener {
                    if(user == it.get("nombre")) {
                        tvrecord.text = game + " " + it.get(game).toString()
                        totalPoints = (points.toInt() + it.get(game).toString().toInt()).toString()
                        id = i
                        i = size
                    }
                }
                i++
            }
        }
        db2.collection("Usuarios").document(id.toString()).set(
            hashMapOf(game to totalPoints)
        )
        tvPoints.text = game+" "+points
    }

}