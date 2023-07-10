package com.example.noms.activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.noms.R
import com.example.noms.ViewModel.MealVievModel
import com.example.noms.databinding.ActivityMainBinding
import com.example.noms.databinding.ActivityMealBinding
import com.example.noms.fragments.HomeFragment
import com.example.noms.pojo.Meal

class MealActivity : AppCompatActivity() {

    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding :ActivityMealBinding
    private lateinit var mealYouTubeLink:String
    private lateinit var mealMvvm:MealVievModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm=ViewModelProvider(this)[MealVievModel::class.java]
        getMealInformationFromIntent()
        setInfromationInViews()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYouTubeImageclick()
    }

    private fun onYouTubeImageclick() {

        binding.imgYoutube.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mealYouTubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMvvm.observMealDetailLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal=value

                binding.tvCategories.text="Category : ${meal.strCategory}"
                binding.tvArea.text="Area : ${meal.strArea}"
                binding.tvInstructionsStep.text=meal.strInstructions

                mealYouTubeLink=value.strYoutube.toString()

            }


        })
    }

    private fun setInfromationInViews() {

        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolBar.title=mealName
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {

        val intent=intent

        mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun loadingCase(){
        binding.progressBar.visibility=View.VISIBLE
        binding.btnAddToFav.visibility= View.INVISIBLE
        binding.tvInstructions.visibility= View.INVISIBLE
        binding.tvCategories.visibility= View.INVISIBLE
        binding.tvArea.visibility= View.INVISIBLE
        binding.imgYoutube.visibility= View.INVISIBLE
    }

    private fun  onResponseCase(){
        binding.progressBar.visibility=View.INVISIBLE
        binding.btnAddToFav.visibility= View.VISIBLE
        binding.tvInstructions.visibility= View.VISIBLE
        binding.tvCategories.visibility= View.VISIBLE
        binding.tvArea.visibility= View.VISIBLE
        binding.imgYoutube.visibility= View.VISIBLE
    }
}