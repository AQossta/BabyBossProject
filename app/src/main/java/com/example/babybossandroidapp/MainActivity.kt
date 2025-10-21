package com.example.babybossandroidapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.babybossandroidapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationHostProvider {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun setNavigationVisibility(visibility: Boolean) {
        null
    }
}