package com.example.project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.TreeSet

class SharedViewModel : ViewModel() {
  val checkedGenreFilters = MutableLiveData<Filters>()
  val checkedDirectorFilters = MutableLiveData<Filters>()
  val checkedYearFilters = MutableLiveData<Filters>()

  var yearsList = TreeSet<Int>()
  var directorsList = TreeSet<String>()
  var genresList = TreeSet<String>()

  fun updateList(years: Filters, genres: Filters, directors: Filters) {
    checkedYearFilters.value = years
    checkedDirectorFilters.value = directors
    checkedGenreFilters.value = genres

  }

  fun isNotEmpty(): Boolean {
    return when {
      checkedYearFilters.value == null -> false
      checkedDirectorFilters.value == null -> false
      checkedGenreFilters.value == null -> false
      checkedYearFilters.value!!.isNotEmpty() -> true
      checkedDirectorFilters.value!!.isNotEmpty() -> true
      checkedGenreFilters.value!!.isNotEmpty() -> true
      else -> false
    }
  }

}