package com.example.noms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.noms.databinding.MealItemBinding
import com.example.noms.pojo.Meal

class FavoritesMealAdapter:RecyclerView.Adapter<FavoritesMealAdapter.FavoritesMealsViewHolder>() {

    var onFavoriteItemClick :((Meal)->Unit)?=null
    inner class FavoritesMealsViewHolder(val binding:MealItemBinding):RecyclerView.ViewHolder(binding.root){
    }

    private val diffUtil =object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {

            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {

            return  oldItem==newItem
        }

    }

    val differ =AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMealsViewHolder {
        return FavoritesMealsViewHolder(
            MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {

       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesMealsViewHolder, position: Int) {
        val meal =differ.currentList[position]

        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.mealName.text=meal.strMeal
        holder.itemView.setOnClickListener {

            onFavoriteItemClick?.invoke(meal)
        }

    }
}