package com.example.noms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.noms.databinding.CategoryItemBinding
import com.example.noms.databinding.MealItemBinding
import com.example.noms.pojo.MealsByCategory

class CategoryMealAdapter:RecyclerView.Adapter<CategoryMealAdapter.CategoryMealsViewModel>() {

    private var mealsList = ArrayList<MealsByCategory>()

    fun setMealsList(mealList:List<MealsByCategory>){

        this.mealsList=mealList as ArrayList<MealsByCategory>
    }

    inner class CategoryMealsViewModel(var binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
        val binding = MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryMealsViewModel(binding)
    }

    override fun getItemCount(): Int {

        return  mealsList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {

        val meal=mealsList[position]

        Glide.with(holder.itemView)
            .load(meal.strMealThumb).into(holder.binding.imgMeal)

        holder.binding.mealName.text=meal.strMeal
    }
}