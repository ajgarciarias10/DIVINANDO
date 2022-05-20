package org.izv.jmunoz.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    lateinit var tvDiv: TextView
    lateinit var tvDivTil: TextView
    lateinit var tvEnc: TextView
    lateinit var tvPais: TextView
    lateinit var tvEscudo: TextView
    lateinit var tvFam: TextView
    private val db = FirebaseFirestore.getInstance()
    var userName = "Sonia"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()

    }

    private fun init(){
        tvDiv = findViewById(R.id.tvDivHome)
        tvDivTil = findViewById(R.id.tvDivTilHome)
        tvEnc = findViewById(R.id.tvEncHome)
        tvPais = findViewById(R.id.tvPaisHome)
        tvEscudo = findViewById(R.id.tvEscudoHome)
        tvFam = findViewById(R.id.tvFamhome)

        getRecord("divinando", tvDiv)
        getRecord("divtilde", tvDivTil)
        getRecord("encadenados", tvEnc)
        getRecord("paises", tvPais)
        getRecord("escudos", tvEscudo)
        getRecord("famosos", tvFam)
    }

    private fun getRecord(s: String, t: TextView){
        db.collection("Usuarios").get().addOnSuccessListener {
            var i = 0
            var size = it.documents.size
            t.text = "0"
            while(i in 0..size){
                db.collection("Usuarios").document(i.toString()).get().addOnSuccessListener {
                    if(userName == it.get("nombre").toString()) {
                        t.text = s.uppercase()+"    "+it.get(s).toString()
                    }
                }
                i++
            }
        }
    }

}