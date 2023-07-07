package com.example.noms.fragments


import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.noms.R
import com.example.noms.ViewModel.HomeViewModel
import com.example.noms.activites.MealActivity
import com.example.noms.adapters.MostPopularAdapter
import com.example.noms.databinding.FragmentHomeBinding
import com.example.noms.pojo.CategoryMeals
import com.example.noms.pojo.Meal
import com.example.noms.pojo.MealList
import com.example.noms.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeVM:HomeViewModel
    private lateinit var randomMeal:Meal

    private lateinit var popularItemsAdapter:MostPopularAdapter

    companion object{

        const val MEAL_ID ="com.example.noms.fragments.idMeal"
        const val MEAL_NAME ="com.example.noms.fragments.nameMeal"
        const val MEAL_THUMB ="com.example.noms.fragments.thumbMeal"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeVM = ViewModelProvider(this)[HomeViewModel::class.java]

        popularItemsAdapter= MostPopularAdapter()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        homeVM.getRandomMeal()
        observerRandomMeal()

        onRandomMealClick()

        homeVM.getPopularMeals()
        observerPopularItemLiveData()

        onPopularItemClick()


    }

    private fun onPopularItemClick() {
        popularItemsAdapter.itemClicked = { meal->

            val intent = Intent(activity,MealActivity::class.java)

            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)

            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {

            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=popularItemsAdapter
        }
    }

    private fun observerPopularItemLiveData() {
        homeVM.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList->

            popularItemsAdapter.setMeals(mealList as ArrayList<CategoryMeals>)
        }
    }

    private fun onRandomMealClick() {

        binding.randomMealCard.setOnClickListener{

            val intent= Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun observerRandomMeal(){

        homeVM.obeserveRandomMealLiveData().observe(viewLifecycleOwner,object :Observer<Meal>{
            override fun onChanged(value: Meal) {

                Glide.with(this@HomeFragment)
                    .load(value!!.strMealThumb)
                    .into(binding.imgRandomMeal)

                this@HomeFragment.randomMeal=value
            }

        })
    }


}