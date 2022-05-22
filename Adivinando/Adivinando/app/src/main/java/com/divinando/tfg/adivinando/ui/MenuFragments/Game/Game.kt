package com.divinando.tfg.adivinando.ui.MenuFragments.Game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.divinando.tfg.adivinando.databinding.GameFragmentBinding
import com.divinando.tfg.adivinando.model.entity.GameObjeto
import com.divinando.tfg.adivinando.ui.MainActivity
import com.divinando.tfg.adivinando.view.adapter.GameAdapter


class Game : Fragment() {


    private var _binding: GameFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = GameFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //region  SETEAR EL ADAPTADOR

        binding.rv.layoutManager = LinearLayoutManager(context)
            val gameAdapter = GameAdapter(requireContext())
        binding.rv.adapter = gameAdapter
            val games: ArrayList<GameObjeto> = MainActivity.games
            gameAdapter.setGameList(games)
        //endregion
    }



}