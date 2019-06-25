package com.example.project

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class FilterItem : BaseObservable() {
  var title: String = ""

  @get:Bindable var state = false
    set(value) {
      if (field != value) {
        field = value
        notifyPropertyChanged(BR.state)
      }
    }

}