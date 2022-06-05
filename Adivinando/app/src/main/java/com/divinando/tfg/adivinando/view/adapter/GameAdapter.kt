package com.divinando.tfg.adivinando.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.divinando.tfg.adivinando.R
import com.divinando.tfg.adivinando.model.entity.GameObjeto
import com.divinando.tfg.adivinando.view.adapter.viewholder.GameViewHolder

class GameAdapter(context: Context) : RecyclerView.Adapter<GameViewHolder>() {
    var gamee: ArrayList<GameObjeto>? = ArrayList()
    private val context: Context = context

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): GameViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: GameViewHolder, position: Int) {
        val gameObjeto: GameObjeto = gamee!![position] //Obtenemos la posicion del juego
        //region Seteamos el texto
            holder.objeto = gameObjeto
            holder.tvGameTitle.text = gameObjeto.nombre
            holder.tvGameDesc.text = gameObjeto.descripcionJuego
            Glide.with(context).load(gameObjeto.imagenDelJuego)
                .override(80,80).error(R.mipmap.ic_launcher).into(holder.ivGameIcon)
        //enddregion
    }

    override fun getItemCount(): Int {
        return if (gamee == null) {
            0
        } else gamee!!.size
    }


    fun setGameList(gamee: ArrayList<GameObjeto>) {
        this.gamee = gamee
    }

}