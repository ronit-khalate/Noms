package com.example.noms.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.noms.ViewModel.HomeViewModel
import com.example.noms.activites.MainActivity
import com.example.noms.activites.MealActivity
import com.example.noms.adapters.MealsAdapter
import com.example.noms.databinding.FragmentFavoritesBinding
import com.google.android.material.snackbar.Snackbar


class FavoritesFragment() : Fragment() {

    private lateinit var binding:FragmentFavoritesBinding
    private lateinit var viewMode:HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var mealsAdapter: MealsAdapter

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

        val itemTouchHelper=object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )=true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val currentMeal=mealsAdapter.differ.currentList[position]
                viewMode.deleteMeal(mealsAdapter.differ.currentList[position])

                Snackbar.make(requireView(),"Meal Deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo"
                ) {

                    viewMode.insertMeal(currentMeal)
                }.show()

            }



        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rcFavorites)

        onFavoriteItemClick()
    }

    private fun onFavoriteItemClick() {
        mealsAdapter.onFavoriteItemClick={

            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,it.strCategory)
            intent.putExtra(HomeFragment.MEAL_NAME,it.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB,it.strMealThumb)
            intent.putExtra(HomeFragment.MEAL_ID,it.idMeal)

            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {

        mealsAdapter= MealsAdapter()
       binding.rcFavorites.apply {

           layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
           adapter=mealsAdapter
       }
    }

    private fun observeFavorites() {
        viewMode.observeFavoriteMealsLiveData().observe(viewLifecycleOwner){

            mealsAdapter.differ.submitList(it)

        }
    }


}