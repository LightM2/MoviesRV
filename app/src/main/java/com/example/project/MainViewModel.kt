package com.example.project

import android.util.Log
import androidx.navigation.Navigation

class MainViewModel : BaseViewModel() {

  fun onClick() {
    Log.d("Filters", "Click ToolBar")
    Navigation.createNavigateOnClickListener(R.id.toFilters)
  }

}
