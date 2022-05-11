package com.divinando.tfg.adivinando.model.entity

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class GameObjeto(modoJuego: String, nombre: String, idioma: String, imagenDelJuego: String, imagenUrl: ArrayList<String>?, listaPalabras: ArrayList<String>?, descripcionJuego: String, numeroRondas: Int,vof: ArrayList<Boolean>?) :
    Parcelable {
    var modoJuego: String? = null
    var nombre: String? = null
    var idioma: String? = null
    var imagenDelJuego : String? = null
    var imagenUrl: ArrayList<String>? = null
    var listaPalabras: ArrayList<String>? = null
    var descripcionJuego : String? = null
    var numeroRondas = 0
    var vof : ArrayList<Boolean>? = null

    constructor(parcel: Parcel) : this(
        TODO("modoJuego"),
        TODO("nombre"),
        TODO("idioma"),
        TODO("imagenDelJuego"),
        TODO("imagenUrl"),
        TODO("listaPalabras"),
        TODO("descripcionJuego"),
        TODO("numeroRondas"),
        TODO("vof")
    ) {
        modoJuego = parcel.readString()
        nombre = parcel.readString()
        idioma = parcel.readString()
        imagenDelJuego = parcel.readString()
        descripcionJuego = parcel.readString()
        numeroRondas = parcel.readInt()
    }


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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(modoJuego)
        parcel.writeString(nombre)
        parcel.writeString(idioma)
        parcel.writeString(imagenDelJuego)
        parcel.writeString(descripcionJuego)
        parcel.writeInt(numeroRondas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameObjeto> {
        override fun createFromParcel(parcel: Parcel): GameObjeto {
            return GameObjeto(parcel)
        }

        override fun newArray(size: Int): Array<GameObjeto?> {
            return arrayOfNulls(size)
        }
    }


}