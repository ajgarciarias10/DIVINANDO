package com.divinando.tfg.adivinando.view.adapter.viewholder

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.navigation.Navigation
import com.divinando.tfg.adivinando.R
import com.divinando.tfg.adivinando.model.entity.GameObjeto

class GameViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

    var ivGameIcon: ImageView = itemView.findViewById(R.id.ivGameIcon)
    var tvGameTitle: TextView = itemView.findViewById(R.id.tvGameTitle)
    var tvGameDesc: TextView = itemView.findViewById(R.id.tvGameDesc)
    var gameObjeto: GameObjeto? = null
    private var bundle: Bundle? = null

    init {
        /*
itemView.setOnClickListener { v ->
   bundle = Bundle()
   bundle!!.putParcelable("juego", gameObjeto)

   when (itemViewType) {
        0 -> {
            Navigation.findNavController(itemView)
                .navigate(R.id., bundle)
       }
       1 -> {
           Navigation.findNavController(itemView)
               .navigate(R.id., bundle)
       }
       2 -> {
           Navigation.findNavController(itemView)
               .navigate(R.id., bundle)
       } 3 -> {
           Navigation.findNavController(itemView)
               .navigate(R.id., bundle)
       } 4 -> {
           Navigation.findNavController(itemView)
               .navigate(R.id., bundle)
       } 5 -> {
           Navigation.findNavController(itemView)
               .navigate(R.id., bundle)
       } 6 -> {
           Navigation.findNavController(itemView)
               .navigate(R.id., bundle)
       }

   }
   }
    */

   }



}