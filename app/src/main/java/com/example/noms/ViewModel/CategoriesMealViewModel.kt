package com.example.noms.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.noms.pojo.MealsByCategory
import com.example.noms.pojo.MealsByCategoryList
import com.example.noms.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesMealViewModel :ViewModel(){

    val mealsLiveData=MutableLiveData<List<MealsByCategory>>()
    fun getMealsByCategory(categoryName:String){

        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let {
                    mealsLiveData.postValue(it.meals)
                }

            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
               Log.d("CategoryMealViewMode",t.message.toString())
            }

        })


    }

    fun observMealsLiveData():LiveData<List<MealsByCategory>>{
        return mealsLiveData
    }
}