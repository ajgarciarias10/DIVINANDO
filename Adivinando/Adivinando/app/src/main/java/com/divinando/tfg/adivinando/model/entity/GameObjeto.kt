package com.divinando.tfg.adivinando.model.entity

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class GameObjeto(modoJuego: String, nombre: String, idioma: String, ImagenDelJuego: String, imagenUrl: ArrayList<String>?, listaPalabras: ArrayList<String>?, descripcionJuego: String, numeroRondas: Int)   {
    var modoJuego: String? = null
    var nombre: String? = null
    var idioma: String? = null
    var ImagenDelJuego : String? = null
    var imagenUrl: ArrayList<String>? = null
    var listaPalabras: ArrayList<String>? = null
    var descripcionJuego : String? = null
    var numeroRondas = 0


    init {
        this.modoJuego = modoJuego
        this.nombre = nombre
        this.idioma = idioma
        this.imagenUrl = imagenUrl
        this.listaPalabras = listaPalabras
        this.descripcionJuego = descripcionJuego
        this.numeroRondas = numeroRondas
    }




}