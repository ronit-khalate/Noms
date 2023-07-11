package com.example.noms.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.noms.R
import com.example.noms.ViewModel.HomeViewModel
import com.example.noms.activites.CategoryMealActivity
import com.example.noms.activites.MainActivity
import com.example.noms.adapters.CategoriesAdapter
import com.example.noms.databinding.FragmentCategoriesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment : Fragment() {


    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel:HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentCategoriesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparerecyclerView()
        observeCategories()
        onCategoryMealClick()

    }

    private fun onCategoryMealClick() {
        categoriesAdapter.onItemClick={
            val intent=Intent(activity,CategoryMealActivity::class.java)

            intent.putExtra(HomeFragment.CATEGORY_NAME,it.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategories() {

        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner){
            categoriesAdapter.setCategoryList(it)

        }
    }

    private fun preparerecyclerView() {

        categoriesAdapter= CategoriesAdapter()
        binding.rvCatrgories.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }


}