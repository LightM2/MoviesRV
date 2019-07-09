package com.example.project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.TreeSet

class SharedViewModel : ViewModel() {
  val checkedYearFilters = MutableLiveData<Filters>()

  var yearsList = TreeSet<Int>()
  var directorsList = TreeSet<String>()
  var genresList = TreeSet<String>()

  fun updateList(item: Filters) {
    checkedYearFilters.value = item
  }

}