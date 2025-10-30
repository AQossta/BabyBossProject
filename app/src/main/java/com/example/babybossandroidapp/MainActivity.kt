package com.example.babybossandroidapp

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.babybossandroidapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationHostProvider {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun setNavigationVisibility(visibility: Boolean) {
        null
    }
}