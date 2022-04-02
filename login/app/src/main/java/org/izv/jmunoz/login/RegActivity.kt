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
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

class RegActivity : AppCompatActivity() {

    lateinit var btRegister: Button
    lateinit var etName: EditText
    lateinit var etMail: EditText
    lateinit var etPass: EditText
    lateinit var etConfirm: EditText
    lateinit var navigate: Intent
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

        btRegister = findViewById<Button>(R.id.btRegAdd)
        navigate = Intent(this,MainActivity::class.java)
        etName = findViewById(R.id.etNameReg)
        etMail = findViewById(R.id.etMailReg)
        etPass = findViewById(R.id.etPassReg)
        etConfirm = findViewById(R.id.etConfirmPass)

        btRegister.setOnClickListener{
            saveData(it)
        }

    }

    private fun saveData(view: View){
        var msg = ""

        if(!validateText()){
            msg = "Empty field(s), please check it"
        }
        else if(etName.text.length < 4){
            msg = "Name 4 characters min, check it"
        }
        else if(!validateMail()){
            msg = "Wrong mail, please check it"
        }
        else if(etPass.text.length < 8 || etConfirm.text.length < 8){
            msg = "Pass and confirm pass 8 characters min"
        }
        else if(!validatePass()){
            msg = "Pass and confirm pass are diferent"
        }

        if(msg == ""){
            startActivity(navigate)
        }
        else{
            showSnackBar(view, msg)
        }

    }

    private fun validateText(): Boolean{
        return etName.text.isNotEmpty()     && etPass.text.isNotEmpty() &&
               etConfirm.text.isNotEmpty()  && etMail.text.isNotEmpty()
    }

    private fun validatePass(): Boolean{
        return etPass.text.toString() == etConfirm.text.toString()
    }

    private fun validateMail(): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(etMail.text).matches()
    }

    private fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE).setAction("hidden") {}.show()
    }

}