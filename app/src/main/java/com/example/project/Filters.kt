package com.example.project

import androidx.databinding.ObservableBoolean

class Filters {
  var filtersName: List<String> = listOf()

  var filtersState: MutableList<Boolean> = mutableListOf()

/*  @get:Bindable var filterState: Boolean = true
    set(value) {
      if (field != value) {
        field = value
        notifyPropertyChanged(BR.filterState)
      }
    }*/

  var state: ObservableBoolean = ObservableBoolean(true)

  private fun setFalseFiltersState(){
    for (i in 0 until filtersName.size){
      filtersState.add(false)
    }
  }

  fun addElements(names: List<String>){
    filtersName = names
    setFalseFiltersState()
  }

  fun newFilterState(position: Int, state: Boolean){
    if (position< filtersState.size){
      filtersState[position] = state
    }

  }

  fun size(): Int {
    return filtersName.size
  }

  fun allFilterState(state: Boolean){
    for (i in 0 until filtersState.size){
      filtersState[i] = state
    }
  }

  fun checkAllFiltersOneState(){
    var number = 0
    for (i in 1 until filtersState.size){
      if (filtersState[i]){
        number += 1
      }
    }
    if (number==(filtersState.size-1)){
      filtersState[0]=true
    }
  }
}