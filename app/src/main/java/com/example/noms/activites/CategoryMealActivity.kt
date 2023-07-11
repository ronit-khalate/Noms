package com.example.noms.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noms.R
import com.example.noms.ViewModel.CategoriesMealViewModel
import com.example.noms.adapters.CategoryMealAdapter
import com.example.noms.databinding.ActivityCategoryMealBinding
import com.example.noms.fragments.HomeFragment

class CategoryMealActivity : AppCompatActivity() {

    lateinit var  binding:ActivityCategoryMealBinding
    lateinit var categoryMealViewModel:CategoriesMealViewModel
    private lateinit var categoryMealAdapter: CategoryMealAdapter

    companion object{

        const val MEAL_ID ="com.example.noms.fragments.idMeal"
        const val MEAL_NAME ="com.example.noms.fragments.nameMeal"
        const val MEAL_THUMB ="com.example.noms.fragments.thumbMeal"
        const val CATEGORY_NAME="com.example.noms.fragments.catogryName"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        // Initialising categoryMealViewModel
        categoryMealViewModel=ViewModelProvider(this)[CategoriesMealViewModel::class.java]
        categoryMealViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        // setting category name


        categoryMealViewModel.observMealsLiveData().observe(this) {

            binding.tvCategoryCount.text=it.size.toString()
            categoryMealAdapter.setMealsList(it)
            categoryMealAdapter.notifyDataSetChanged()


        }

        onItemClicked()
    }

    private fun onItemClicked() {
        categoryMealAdapter.onItemclick={
            val intent = Intent(this,MealActivity::class.java)

            intent.putExtra(HomeFragment.MEAL_ID,it.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME,it.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB,it.strMealThumb)

            startActivity(intent)
        }
    }

    private fun prepareRecyclerView(){

        // Initialising Adapter
        categoryMealAdapter= CategoryMealAdapter()

        binding.rvMeals.apply {

            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealAdapter

        }

    }
}