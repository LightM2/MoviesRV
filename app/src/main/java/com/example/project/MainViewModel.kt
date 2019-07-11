package com.example.project

import android.util.Log

class MainViewModel : BaseViewModel() {

  fun onClick() {
    Log.d("Filter", "click")

    // Navigation.findNavController().navigate(R.id.toFilters)

  }
}