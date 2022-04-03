package org.izv.jmunoz.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    lateinit var btRegister: Button
    lateinit var btLog: Button
    lateinit var navigate: Intent
    lateinit var logged: Intent
    lateinit var etMail: EditText
    lateinit var etPass: EditText
    lateinit var firebase: FirebaseAuth
    lateinit var msg: String
    lateinit var py: Python
    lateinit var pyObj: PyObject
    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        btRegister.setOnClickListener{
            startActivity(navigate)
            finish()
        }

        btLog.setOnClickListener {
            validateLogin(it)
        }

    }

    private fun init(){

        msg = ""
        firebase = Firebase.auth

        btRegister = findViewById(R.id.btReg)
        btLog = findViewById(R.id.btLog)
        navigate = Intent(this,RegActivity::class.java)
        logged = Intent(this,GameActivity::class.java)
        etMail = findViewById(R.id.etMailLog)
        etPass = findViewById(R.id.etPass)

    }

    private fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("hidden") {}.show()
    }

    private fun login(view: View){

        firebase.signInWithEmailAndPassword(etMail.text.toString(), etPass.text.toString())
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful) {
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

}