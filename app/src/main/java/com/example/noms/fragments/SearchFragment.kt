package com.example.noms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noms.R
import com.example.noms.ViewModel.HomeViewModel
import com.example.noms.activites.MainActivity
import com.example.noms.adapters.MealsAdapter
import com.example.noms.databinding.FragmentSearchBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding:FragmentSearchBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var searchRecyclerViewAdapter:MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        binding.imgSearchArrow.setOnClickListener {
            searchMeal()
        }

        observeSearchMealLiveData()

        var searchJob: Job?=null

        binding.edSerachBox.addTextChangedListener { searchQuery->
            searchJob?.cancel()
            searchJob=lifecycleScope.launch {
                delay(500)
                viewModel.searchMeal(searchquery = searchQuery.toString())
            }
        }
    }

    private fun observeSearchMealLiveData() {

        viewModel.observeSearchMealsLiveData().observe(viewLifecycleOwner){

            searchRecyclerViewAdapter.differ.submitList(it)
        }
    }

    private fun searchMeal() {
        val searchQuery = binding.edSerachBox.text.toString()

        if(searchQuery.isNotEmpty()){
            viewModel.searchMeal(searchQuery)
        }
    }

    private fun prepareRecyclerView(){

        searchRecyclerViewAdapter=MealsAdapter()
        binding.rvSearch.apply {

            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=searchRecyclerViewAdapter
        }
    }


}