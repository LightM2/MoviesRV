package com.example.project

import dev.auxility.baseadapter.item.Item

class FilterTitleItem(val title: String) : Item {
  override fun getLayoutId(): Int = R.layout.filter_title_tv
}