package com.example.noms.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.noms.R
import com.example.noms.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

////    //? Navigation Setup
        val bottomNavigation=binding.bottomNav
        navController=Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }
}