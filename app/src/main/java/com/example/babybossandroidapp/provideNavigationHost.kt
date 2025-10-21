package com.example.babybossandroidapp

import androidx.fragment.app.Fragment

fun Fragment.provideNavigationHost(): NavigationHostProvider? {
    return try {
        activity as NavigationHostProvider
    } catch (e: Exception) {
        null
    }
}