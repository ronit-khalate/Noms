package com.example.noms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.noms.databinding.PopuerItemBinding
import com.example.noms.pojo.MealsByCategory

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.PopularViewHolder>() {

    private var mealsList=ArrayList<MealsByCategory>()
    lateinit var itemClicked:((MealsByCategory)->Unit)
    var onLongItemclick:((MealsByCategory)->Unit)?=null

    fun  setMeals(mealList: ArrayList<MealsByCategory>){
        this.mealsList=mealList
        notifyDataSetChanged()
    }
    inner class PopularViewHolder( val binding:PopuerItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {

        return PopularViewHolder(PopuerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun getItemCount(): Int {

        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            itemClicked.invoke(mealsList[position])
        }

        holder.itemView.setOnLongClickListener {
            onLongItemclick?.invoke(mealsList[position])
            true
        }
    }
}