package com.divinando.tfg.adivinando.model.entity

import com.google.firebase.firestore.DocumentSnapshot
import java.io.Serializable
import java.util.ArrayList


class GameObjeto(var modoJuego: String,
                 var nombre: String,
                 var idioma: String, var imagenDelJuego: String,
                 var imagenUrl: ArrayList<String>?,
                 var listaPalabras: ArrayList<String>?,
                 var listaUrl: MutableList<DocumentSnapshot>?,
                 var descripcionJuego: String, var numeroRondas: Int,
                 var vof: ArrayList<Boolean>?) :
    Serializable {
    constructor() : this("", "",
        "", "", null,
        null, null,"", 0,
        null
    )
}




