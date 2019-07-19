package com.example.project

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import dev.auxility.baseadapter.item.Item

class FilterCheckBoxItem(private val filter: Filter) : Item, BaseObservable() {

  @Bindable var state: Boolean = false
    get() = filter.state
    set(value) {
      if (field != value) {
        field = value
        notifyPropertyChanged(BR.state)
      }

    }

  fun getTitle(): String = filter.title

  override fun getLayoutId(): Int = R.layout.filter_state_cb
}