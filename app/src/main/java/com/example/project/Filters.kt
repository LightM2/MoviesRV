package com.example.project

import android.util.Log
import androidx.databinding.Bindable
import java.io.Serializable

class Filters(val filtersList: List<FilterItem> = mutableListOf()) : FilterItem("All"),
  Serializable {

  init {
    filtersList.forEach { filter ->
      filter.filter = this
    }
  }

  @get:Bindable override var state: Boolean = false
    set(value) {
      if (field != value) {
        field = value
        if (field) {
          allFilterStateTrue()
        }
        Log.d("state", "$title $value")
        //notifyPropertyChanged(BR.state)
      }
    }

  fun isNotEmpty(): Boolean {
    var count = 0
    filtersList.forEach {
      if (it.state) {
        count++
      }
    }
    return count > 0
  }

  private fun allFilterStateTrue() {
    filtersList.forEachIndexed { _, filterItem ->
      filterItem.state = true
    }
  }



}