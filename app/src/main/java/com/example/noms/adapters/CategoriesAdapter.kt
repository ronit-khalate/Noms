package com.example.noms.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.noms.databinding.CategoryItemBinding
import com.example.noms.databinding.PopuerItemBinding
import com.example.noms.pojo.Category

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categoryList=ArrayList<Category>()
    var onItemClick:((Category)->Unit)?=null
    inner class CategoryViewHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    fun setCategoryList(categoriesList: List<Category>){
        this.categoryList=(categoriesList as ArrayList<Category>)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        return CategoryViewHolder(
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb).into(holder.binding.imdCategory)

        holder.binding.tvCategoryName.text=categoryList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }
    }
}