package com.example.project

import dev.auxility.baseadapter.item.Item

class FilterAllItem(val state: Boolean) : Item {
  override fun getLayoutId(): Int = R.layout.filters_fragment
}