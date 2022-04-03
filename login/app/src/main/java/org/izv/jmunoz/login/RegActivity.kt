package org.izv.jmunoz.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class RegActivity : AppCompatActivity() {

    lateinit var btRegister: Button
    lateinit var etName: EditText
    lateinit var etMail: EditText
    lateinit var etPass: EditText
    lateinit var etConfirm: EditText
    lateinit var navigate: Intent
    lateinit var firebase: FirebaseAuth
    lateinit var py: Python
    lateinit var pyObj: PyObject
    lateinit var msg: String
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
        setContentView(R.layout.activity_reg)

        init()

        btRegister.setOnClickListener{
            saveData(it)
        }

    }

    private fun init(){
        firebase = Firebase.auth
        msg = ""

        btRegister = findViewById<Button>(R.id.btRegAdd)
        navigate = Intent(this,MainActivity::class.java)
        etName = findViewById(R.id.etNameReg)
        etMail = findViewById(R.id.etMailReg)
        etPass = findViewById(R.id.etPassReg)
        etConfirm = findViewById(R.id.etConfirmPass)
    }

    private fun saveData(view: View){

        /*======= START ====== PYTHON =============*/
        if (! Python.isStarted()) {
            Python.start( AndroidPlatform(this));
        }

        py = Python.getInstance()
        pyObj = py.getModule("validateReg") //give python script name
        msg = pyObj.callAttr("main", etMail.text.toString(), etPass.text.toString(),
            etConfirm.text.toString(), etName.text.toString() ).toString()// call function
        /*======= END ====== PYTHON =============*/

        if(!validateMail() && msg == ""){
            msg = "Wrong mail, please check it"
        }

        if(msg == ""){
            register(view)
        }
        else{
            showSnackBar(view, msg)
            msg = ""
        }

    }

    private fun validateMail(): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(etMail.text).matches()
    }

    private fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("hidden") {}.show()
    }

    private fun register(view: View){

        firebase.createUserWithEmailAndPassword(etMail.text.toString(), etPass.text.toString())
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    startActivity(navigate)
                    finish()
                }
                else{
                    showSnackBar(view, "Register failed, please check it")
                }

            }

    }

}