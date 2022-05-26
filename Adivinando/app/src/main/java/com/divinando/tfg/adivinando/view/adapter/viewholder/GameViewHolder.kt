package com.divinando.tfg.adivinando.view.adapter.viewholder

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.navigation.Navigation.findNavController
import com.divinando.tfg.adivinando.R
import com.divinando.tfg.adivinando.model.entity.GameObjeto

class GameViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

    var ivGameIcon: ImageView = itemView.findViewById(R.id.ivGameIcon)
    var tvGameTitle: TextView = itemView.findViewById(R.id.tvGameTitle)
    var tvGameDesc: TextView = itemView.findViewById(R.id.tvGameDesc)
    var objeto= GameObjeto()
    var bundle = Bundle()


        init {
            itemView.setOnClickListener { v ->
               bundle.putSerializable("juegos", objeto)

             //region Navegacion dependiendo de lo que contenga el titulo y donde se clickeeee
                when (tvGameTitle.text) {
                    "Normal" ->  findNavController(itemView).navigate(R.id.action_game_to_normalMode,bundle);
                    "Con Tildes" -> findNavController(itemView).navigate(R.id.action_game_to_tildes,bundle);
                    "Encadenados"-> findNavController(itemView).navigate(R.id.action_game_to_encadenados, bundle)
                    "CountryGuesser" -> findNavController(itemView).navigate(R.id.action_game_to_countryFlags,bundle);
                    "4Chooser" -> findNavController(itemView).navigate(R.id.action_game_to_fourChooser, bundle)
                    "GUESS IT"-> findNavController(itemView).navigate(R.id.action_game_to_guessIT, bundle)
                    else -> {
                        findNavController(itemView).navigate(R.id.action_game_to_nav_home);
                    }
                }
            //endregion
       }


   }



}