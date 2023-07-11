package com.example.noms.activites

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.noms.ViewModel.CategoriesMealViewModel
import com.example.noms.adapters.CategoryMealAdapter
import com.example.noms.databinding.ActivityCategoryMealBinding
import com.example.noms.fragments.HomeFragment

class CategoryMealActivity : AppCompatActivity() {

    lateinit var  binding:ActivityCategoryMealBinding
    lateinit var categoryMealViewModel:CategoriesMealViewModel
    private lateinit var categoryMealAdapter: CategoryMealAdapter

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