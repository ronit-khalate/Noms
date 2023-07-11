package com.example.noms.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noms.db.MealDataBase

class MealViewModelFactory(private val mealDataBase: MealDataBase):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  MealViewModel(mealDataBase) as T
    }
}