package com.divinando.tfg.adivinando.model.entity

import java.io.Serializable
import java.util.ArrayList


class GameObjeto(modoJuego: String, nombre: String, idioma: String, imagenDelJuego: String, imagenUrl: ArrayList<String>?, listaPalabras: ArrayList<String>?, descripcionJuego: String, numeroRondas: Int,vof: ArrayList<Boolean>?) :
    Serializable {
    constructor() : this()

    var modoJuego: String? = null
    var nombre: String? = null
    var idioma: String? = null
    var imagenDelJuego: String? = null
    var imagenUrl: ArrayList<String>? = null
    var listaPalabras: ArrayList<String>? = null
    var descripcionJuego: String? = null
    var numeroRondas = 0
    var vof: ArrayList<Boolean>? = null


    init {
        this.modoJuego = modoJuego
        this.nombre = nombre
        this.idioma = idioma
        this.imagenDelJuego = imagenDelJuego
        this.imagenUrl = imagenUrl
        this.listaPalabras = listaPalabras
        this.descripcionJuego = descripcionJuego
        this.numeroRondas = numeroRondas
        this.vof = vof
    }
}




