package com.ieszv.deintyad.backendtoni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //PARA PASAR PARAMETROS ENTRE ACTIVIDADES SE UTILIZA BUNDLE

        // getting the bundle back from the android
        val bundle = intent.extras
        // performing the safety null check
        var email :String? = null
        // getting the string back
        email= bundle!!.getString("email", "Default")

            var  tv : TextView = findViewById(R.id.tv_Wel)
            tv.setText(email)

    }
}