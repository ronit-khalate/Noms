package com.example.noms.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.noms.pojo.Meal
import com.example.noms.pojo.MealList
import com.example.noms.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {

    private var randomMealLiveData: MutableLiveData<Meal> = MutableLiveData()
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

    fun obeserveRandomMealLiveData():LiveData<Meal>{

        return  randomMealLiveData
    }
}