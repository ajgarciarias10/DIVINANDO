package org.izv.jmunoz.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var btRegister: Button
    lateinit var btLog: Button
    lateinit var navigate: Intent
    lateinit var logged: Intent
    lateinit var etName: EditText
    lateinit var etPass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btRegister = findViewById(R.id.btReg)
        btLog = findViewById(R.id.btLog)
        navigate = Intent(this,RegActivity::class.java)
        logged = Intent(this,GameActivity::class.java)
        etName = findViewById(R.id.etName)
        etPass = findViewById(R.id.etPass)

        btRegister.setOnClickListener{
            startActivity(navigate)
        }

        btLog.setOnClickListener {
            validate(it)
        }

    }

    private fun validate(view: View){
        var msg = ""
        if(etName.text.isEmpty() || etPass.text.isEmpty()){
            msg = "Empty field(s), please check it"
        }
        else if(etName.text.length < 4){
            msg = "Name field 4 characters min"
        }
        else if(etPass.text.length < 8){
            msg = "Pass field 8 characters min"
        }
        else if(!exist()){
            msg = "Wrong name or mail, check it"
        }

        if(msg == ""){
            startActivity(logged)
        }
        else{
            showSnackBar(view, msg)
        }

    }

    private fun exist(): Boolean{
        return true
    }

    private fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE).setAction("hidden") {}.show()
    }

}