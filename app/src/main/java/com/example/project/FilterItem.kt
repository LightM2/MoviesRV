package com.example.project

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import dev.auxility.baseadapter.item.Item
import java.io.Serializable

open class FilterItem(val title: String) : BaseObservable(), Serializable, Item {
  override fun getLayoutId(): Int = R.layout.filters_list_rv
  lateinit var filter: Filters

  @get:Bindable open var state = false
    set(value) {
      if (field != value) {
        field = value
        Log.d("state", "$title $value")
        if (!field) {
          checkedStateAll(filter)
        } else {
          checkedStates(filter)
        }

        //notifyPropertyChanged(BR.state)

      }
    }

  private fun checkedStateAll(filterAll: FilterItem) {
    if (filterAll.state) {
      filterAll.state = false
    }
  }

  private fun checkedStates(filterAll: FilterItem) {
    val checkedCB = filter.filtersList.count { filterItem -> filterItem.state }

    if (checkedCB == filter.filtersList.size) {
      filterAll.state = true
    }
  }



}