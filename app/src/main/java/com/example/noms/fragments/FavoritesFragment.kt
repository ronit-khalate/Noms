package com.example.noms.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noms.R
import com.example.noms.ViewModel.HomeViewModel
import com.example.noms.activites.MainActivity
import com.example.noms.adapters.FavoritesMealAdapter
import com.example.noms.databinding.FragmentFavoritesBinding


class FavoritesFragment : Fragment() {

    private lateinit var binding:FragmentFavoritesBinding
    private lateinit var viewMode:HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoritesMealAdapter: FavoritesMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMode =(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()
    }

    private fun prepareRecyclerView() {

        favoritesMealAdapter= FavoritesMealAdapter()
       binding.rcFavorites.apply {

           layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
           adapter=favoritesMealAdapter
       }
    }

    private fun observeFavorites() {
        viewMode.observeFavoriteMealsLiveData().observe(viewLifecycleOwner){

            favoritesMealAdapter.differ.submitList(it)

        }
    }


}