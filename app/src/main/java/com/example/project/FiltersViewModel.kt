package com.example.project

import android.util.Log
import dev.auxility.baseadapter.Adapter
import dev.auxility.baseadapter.BaseAdapter
import dev.auxility.baseadapter.item.Item

class FiltersViewModel : BaseViewModel() {

  var filters = mapOf<String, List<Filter>>()

  override val adapter: Adapter<Item> =
    BaseAdapter(
      listOf(
        FilterTitleItem("w"),
        FilterCheckBoxItem(Filter("s", false))
      )

    )

  fun onClick() {
    Log.d("Filter", "click")
  }//+yearFilters.size+genreFilters.size+directorFilters.size
}