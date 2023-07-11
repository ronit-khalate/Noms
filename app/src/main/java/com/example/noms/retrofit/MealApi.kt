package com.example.noms.retrofit

import com.example.noms.pojo.CategoryList
import com.example.noms.pojo.MealsByCategoryList
import com.example.noms.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php")
    fun getMealDetail(@Query("i") id:String):Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName:String):Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c")categoryName: String):Call<MealsByCategoryList>

    @GET("search.php")
    fun searchMeal(@Query("s")searchQuery: String) :Call<MealList>
}