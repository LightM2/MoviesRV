package com.example.project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.TreeSet

class SharedViewModel : ViewModel() {
  val checkedGenreFilters = MutableLiveData<Filters>()
  val checkedDirectorFilters = MutableLiveData<Filters>()
  val checkedYearFilters = MutableLiveData<Filters>()

  var filters = mapOf<String, List<String>>()

  var yearsList = TreeSet<Int>()
  var directorsList = TreeSet<String>()
  var genresList = TreeSet<String>()

  fun updateList(years: Filters, genres: Filters, directors: Filters) {
    checkedYearFilters.value = years
    checkedDirectorFilters.value = directors
    checkedGenreFilters.value = genres

  }

  fun deleteAllFilters() {
    val yearList = yearsList.toList()
    checkedYearFilters.value =
      Filters(yearList.mapIndexed { i, _ -> FilterItem(yearList[i].toString()) })

    val genreList = genresList.toList()
    checkedGenreFilters.value = Filters(genreList.mapIndexed { i, _ -> FilterItem(genreList[i]) })

    val directorList = directorsList.toList()
    checkedDirectorFilters.value =
      Filters(directorList.mapIndexed { i, _ -> FilterItem(directorList[i]) })

  }

  fun isNotEmpty(): Boolean {
    return checkedYearFilters.value?.isNotEmpty() ?: false ||
        checkedDirectorFilters.value?.isNotEmpty() ?: false ||
        checkedGenreFilters.value?.isNotEmpty() ?: false
  }

}