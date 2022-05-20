package org.izv.jmunoz.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class RankingActivity : AppCompatActivity() {

    lateinit var tvDiv: TextView
    lateinit var tvDivTil: TextView
    lateinit var tvEnc: TextView
    lateinit var tvPais: TextView
    lateinit var tvEscudo: TextView
    lateinit var tvFama: TextView

    var size = 0
    var divinando = "0"
    var divUser = ""
    var divTilde = "0"
    var divTilUser = ""
    var encadenados = "0"
    var encUser = ""
    var paises = "0"
    var paisUser = ""
    var escudos = "0"
    var escUser = ""
    var famosos = "0"
    var famUser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        init()

    }

    private fun init() {
        tvDiv = findViewById(R.id.tvDivRanking)
        tvDivTil = findViewById(R.id.tvDivTilRanking)
        tvEnc = findViewById(R.id.tvEncRanking)
        tvPais = findViewById(R.id.tvPaisRanking)
        tvEscudo = findViewById(R.id.tvEscudoRanking)
        tvFama = findViewById(R.id.tvFamRanking)

        val db = FirebaseFirestore.getInstance()
        db.collection("Usuarios").get().addOnSuccessListener {
            readUsers(it.documents.size)
        }

    }

    private fun compareRecord(record: String, point: String): Boolean {
        Log.v("XYZYX", "RECORD: $record     userPoint: $point")
        if (point.toInt() > record.toInt()) {
            return true
        }
        return false
    }

    private fun getData(i: String) {
        val db2 = FirebaseFirestore.getInstance()
                db2.collection("Usuarios").document(i).get().addOnSuccessListener {
                    if (compareRecord(divinando, it.get("divinando").toString())) {
                        divinando = it.get("divinando").toString()
                        divUser = it.get("nombre").toString()
                    }
                    if (compareRecord(divTilde, it.get("divtilde").toString())) {
                        divTilde = it.get("divtilde").toString()
                        divTilUser = it.get("nombre").toString()
                    }
                    if (compareRecord(encadenados, it.get("encadenados").toString())) {
                        encadenados = it.get("encadenados").toString()
                        encUser = it.get("nombre").toString()
                    }
                    if (compareRecord(paises, it.get("paises").toString())) {
                        paises = it.get("paises").toString()
                        paisUser = it.get("nombre").toString()
                    }
                    if (compareRecord(escudos, it.get("escudos").toString())) {
                        escudos = it.get("escudos").toString()
                        escUser = it.get("nombre").toString()
                    }
                    if (compareRecord(famosos, it.get("famosos").toString())) {
                        famosos = it.get("famosos").toString()
                        famUser = it.get("nombre").toString()
                    }
                    setText()
                    Log.v("XYZYX", "$divUser  $divTilUser  $encUser  $paisUser  $escUser  $famUser")
                }
    }

    private fun readUsers(size: Int){
        var i = 0

        while(i in 0 until size) {
            Log.v("XYZYX", "SIZE $size  I: $i")
            getData(i.toString())
            i++
        }
    }

    private fun setText(){
        tvDiv.text = "Divinando  $divinando  $divUser"
        tvDivTil.text = "Div. Tildes  $divTilde  $divTilUser"
        tvEnc.text = "Encadenados  $encadenados  $encUser"
        tvPais.text = "Paises  $paises  $paisUser"
        tvEscudo.text = "Escudos  $escudos  $escUser"
        tvFama.text = "Famosos  $famosos  $famUser"
    }

}
