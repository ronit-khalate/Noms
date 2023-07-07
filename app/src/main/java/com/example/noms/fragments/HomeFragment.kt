package com.example.noms.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noms.R
import com.example.noms.databinding.FragmentHomeBinding
import com.example.noms.pojo.Meal
import com.example.noms.pojo.mealList
import com.example.noms.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RetrofitInstance.api.getRandomMeal().enqueue(object :Callback<mealList>{
            override fun onResponse(call: Call<mealList>, response: Response<mealList>) {

                if(response.body()!=null){
                    val randomMeal :Meal= response.body()!!.meals[0]

                    Log.d("TEST","meal id ${randomMeal.idMeal} name ${randomMeal.strMeal}")
                }
                else{

                    return
                }
            }

            override fun onFailure(call: Call<mealList>, t: Throwable) {

                Log.d("HomeFragment" ,t.message.toString())
            }


        })
    }


}