package com.divinando.tfg.adivinando.view.adapter.viewholder

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.divinando.tfg.adivinando.R
import com.divinando.tfg.adivinando.model.entity.GameObjeto

class GameViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

    var ivGameIcon: ImageView = itemView.findViewById(R.id.ivGameIcon)
    var tvGameTitle: TextView = itemView.findViewById(R.id.tvGameTitle)
    var tvGameDesc: TextView = itemView.findViewById(R.id.tvGameDesc)
    var gameObjeto: GameObjeto? = null
    private var bundle: Bundle? = null


        init {
            itemView.setOnClickListener { v ->
               bundle = Bundle()
               bundle!!.putParcelable("juego", gameObjeto)

             //region Navegacion dependiendo de lo que contenga el titulo y donde se clickeeee

                if ( tvGameTitle.text == "Normal"){
                    findNavController(v).navigate(R.id.action_game_to_normalMode);
                }else if(tvGameTitle.text == ""){
                    findNavController(v).navigate(R.id.action_game_to_nav_home);
                }





       }


   }



}