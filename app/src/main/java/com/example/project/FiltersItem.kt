package com.example.project

import dev.auxility.baseadapter.item.Item

class FiltersItem(val title: String, val state: Boolean) : Item {
  override fun getLayoutId(): Int = R.layout.filters_list_rv

}