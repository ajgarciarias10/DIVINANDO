package org.izv.jmunoz.login

import android.content.ClipData
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class ClubActivity : AppCompatActivity() {

    lateinit var iClub: ImageView
    lateinit var tvWord: TextView
    lateinit var tvRound: TextView
    lateinit var tvPoints: TextView
    lateinit var btNext: Button
    lateinit var btBack: Button
    var items: ArrayList<ClipData.Item> = ArrayList()
    lateinit var sr: StorageReference
    var round = 0
    var points = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club)

        init()

        btNext.setOnClickListener {
            if(comparation()){
                startActivity(Intent(this,EndActivity::class.java))
            }
            else{
                tvWord.text = ""
            }
        }

    }

    private fun init(){
        iClub = findViewById(R.id.iClub)
        tvWord = findViewById(R.id.tvWordClub)
        tvRound = findViewById(R.id.tvRound)
        tvRound.text = "ROUND 1"
        tvPoints = findViewById(R.id.tvPoints)
        tvPoints.text ="0 POINTS"
        btNext = findViewById(R.id.btNextClub)
        btBack = findViewById(R.id.btBackClub)
        sr = FirebaseStorage.getInstance().reference
        sr.listAll().addOnCompleteListener {
            it.result.items.forEachIndexed { index, item ->
                item.downloadUrl.addOnSuccessListener {
                    items.add(ClipData.Item(it.toString()))
                }.addOnCompleteListener {

                    Picasso.get().load(items.get(0).uri).into(iClub)
                }
            }
        }

    }

    private fun comparation(): Boolean{

        if(round > 9){
            return true
        }
        else{
            if(0 == 0){
                points++
                tvPoints.text = ""+points+" POINTS"
            }
            round++
            tvRound.text = "ROUND "+round+""
            return false
        }

    }

}