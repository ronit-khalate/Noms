package com.example.noms.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.noms.R
import com.example.noms.ViewModel.HomeViewModel
import com.example.noms.activites.MainActivity
import com.example.noms.activites.MealActivity
import com.example.noms.databinding.FragmentMealBottomSheetBinding
import com.example.noms.fragments.HomeFragment
import com.example.noms.pojo.Meal
import com.example.noms.pojo.MealList
import com.example.noms.retrofit.RetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val MEAL_ID = "param1"



class MealBottomSheetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var mealId: String? = null
    private lateinit var binding:FragmentMealBottomSheetBinding
    private lateinit var viewModel:HomeViewModel

    private var mealName:String?=null
    private var mealThumb:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)

        }

        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMealBottomSheetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let { viewModel.getMealById(it) }

        observeBottomSheetMeal()

        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener {


            if(mealThumb!=null && mealName!=null){
                val intent=Intent(activity,MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }

                startActivity(intent)
            }
            else{

                Snackbar.make(requireView(),"Something is Wrong..!!!",Snackbar.LENGTH_LONG).show()
            }


        }
    }

    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetLiveData().observe(viewLifecycleOwner){
            Glide.with(this)
                .load(it.strMealThumb)
                .into(binding.imgBottomSheet)

            binding.tvBottomSheetArea.text=it.strArea
            binding.tvBottomSheetMealName.text=it.strMeal
            binding.tvBottomSheetCategory.text=it.strCategory

            mealName=it.strMeal
            mealThumb=it.strMealThumb
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}