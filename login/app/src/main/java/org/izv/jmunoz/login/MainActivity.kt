package org.izv.jmunoz.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.io.BufferedReader
import java.io.File
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    object user {
        lateinit var mail: String
    }

    object gameActive{
        lateinit var game: String
    }

    object userPoints{
        lateinit var points: String
    }

    private lateinit var btRegister: Button
    lateinit var btLog: Button
    lateinit var navigate: Intent
    lateinit var logged: Intent
    lateinit var etMail: EditText
    lateinit var etPass: EditText
    lateinit var firebase: FirebaseAuth
    lateinit var msg: String
    lateinit var py: Python
    lateinit var pyObj: PyObject
    private lateinit var lista: List<String>
    private lateinit var preName: MutableList<String>
    private lateinit var preURL: MutableList<String>
    lateinit var  palabra: Palabra
    private val db = FirebaseFirestore.getInstance()
    private lateinit var words: String
    lateinit var  URL: String
    lateinit var  nombre: String
    private var url = "https://www.listasdepalabras.es/palabras5letras.htm"
    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )



    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        btRegister.setOnClickListener{
            var b = Bundle()
            b.putString("user", etMail.text.toString())
            navigate.putExtras(b)
            startActivity(navigate)
            finish()
        }

        btLog.setOnClickListener {
            validateLogin(it)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(){

        msg = ""
        firebase = Firebase.auth

        btRegister = findViewById(R.id.btReg)
        btLog = findViewById(R.id.btLog)
        navigate = Intent(this,RankingActivity::class.java)
        logged = Intent(this,MenuActivity::class.java)
        etMail = findViewById(R.id.etMailLog)
        etPass = findViewById(R.id.etPass)
        lista = mutableListOf()

        palabra = Palabra()
        lista = palabra.getDB()

        etMail.setText("milito@gmail.com")
        etPass.setText("12345678")
        //getDataPerson()
        //AÑADIR DATOS

        /*for(i in 0..preName.size - 1) {
            db.collection("Famosos").document((i+4).toString()).set(
                hashMapOf("id" to i+4, "nombre" to preName[i], "url" to preURL[i])
            )
        }*/

        //LEER DATOS
        /*db.collection("Palabras").document("TUNOS").get()
            .addOnSuccessListener{
                etMail.setText(it.get("nombre") as String? +"   "+ it.get("id").toString())
            }*/
        //ELIMINAR DATOS
        //db.collection("palabras").document("abrir").delete()

    }

    private fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("hidden") {}.show()
    }

    private fun login(view: View){

        firebase.signInWithEmailAndPassword(etMail.text.toString(), etPass.text.toString())
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful) {
                    user.mail = etMail.text.toString()
                    startActivity(logged)
                    finish()
                }
                else{
                    showSnackBar(view,"Login failed, please check it")
                }
            }

    }

    private fun validateMail(): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(etMail.text).matches()
    }

    private fun validateLogin(view: View){

    /*======= START ====== PYTHON =============*/
        if (! Python.isStarted()) {
            Python.start( AndroidPlatform(this));
        }

        py = Python.getInstance()
        pyObj = py.getModule("validateLog") //give python script name
        msg = pyObj.callAttr("main", etMail.text.toString(), etPass.text.toString()).toString() // call function
    /*======= END ====== PYTHON =============*/

        if(!validateMail() && msg == ""){
            msg = "Invalid mail format, check it"
        }
        if(msg == ""){
            login(view)
        }
        else{
            showSnackBar(view,msg)
            msg =""
        }

    }

    private fun getDataPerson(){
        preName = mutableListOf()
        preName.add("The Rock")
        preName.add("Messi")
        preName.add("Cristiano Ronaldo")
        preName.add("Rafa Nadal")
        preURL = mutableListOf()
        preURL.add("https://foreignpolicyi.org/wp-content/uploads/2019/06/Dwayne-Johnson.jpg")
        preURL.add("https://portal.andina.pe/EDPfotografia3/Thumbnail/2020/10/07/000716490W.jpg")
        preURL.add("https://the18.com/sites/default/files/feature-images/20181004-The18-Image-Cristiano-Ronaldo-Portugal.jpg")
        preURL.add("https://lalupadigital.com/wp-content/uploads/2019/10/908843-1024x708.jpg")
    }

}

