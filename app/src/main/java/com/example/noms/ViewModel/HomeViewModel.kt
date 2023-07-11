package com.example.noms.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noms.db.MealDataBase
import com.example.noms.pojo.Category
import com.example.noms.pojo.CategoryList
import com.example.noms.pojo.MealsByCategoryList
import com.example.noms.pojo.MealsByCategory

import com.example.noms.pojo.Meal
import com.example.noms.pojo.MealList
import com.example.noms.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDataBase: MealDataBase
):ViewModel() {


    private var randomMealLiveData: MutableLiveData<Meal> = MutableLiveData()
    private val popualrItemsLiveData=MutableLiveData<List<MealsByCategory>>()
    private val categoryLiveData=MutableLiveData<List<Category>>()
    private val favoriteMealsLiveData =mealDataBase.mealDao().getAllMeals()
    private var bottomSheetMealLiveData=MutableLiveData<Meal>()
    fun getRandomMeal(){

        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                if(response.body()!=null){

                    val randomMeal : Meal = response.body()!!.meals[0]

                    randomMealLiveData.value=randomMeal

                }else{


                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment" ,t.message.toString())
            }

        })
    }

    fun getPopularMeals(){

        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {

                if(response.body()!=null){

                    popualrItemsLiveData.value=response.body()!!.meals

                }
                else
                    return
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }

        })
    }

    fun getCategories(){

        RetrofitInstance.api.getCategories().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

                response.body()?.let {
                    categoryLiveData.postValue(it.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeViewMode",t.message.toString())
            }

        })
    }

    fun getMealById(id:String){

        RetrofitInstance.api.getMealDetail(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                val meal= response.body()?.meals?.get(0)

                meal?.let {

                    bottomSheetMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
               Log.d("HomeViewModel",t.message.toString())
            }

        })
    }

    fun obeserveRandomMealLiveData():LiveData<Meal>{

        return  randomMealLiveData
    }
    fun observePopularItemsLiveData():LiveData<List<MealsByCategory>>{
        return popualrItemsLiveData
    }

    fun observeCategoryLiveData():LiveData<List<Category>>{
        return categoryLiveData
    }

    fun observeFavoriteMealsLiveData():LiveData<List<Meal>>{

        return favoriteMealsLiveData
    }

    fun observeBottomSheetLiveData():LiveData<Meal>{
        return bottomSheetMealLiveData
    }

    fun deleteMeal(meal: Meal){

        viewModelScope.launch {
            mealDataBase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal:Meal){

        viewModelScope.launch {

            mealDataBase.mealDao().upsert(meal)
        }
    }
}