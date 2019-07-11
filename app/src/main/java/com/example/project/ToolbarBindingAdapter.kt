package com.example.project

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController

class ToolbarBindingAdapter {
  companion object CurrencyBindingAdapter {
    @BindingAdapter("navigationListener")
    @JvmStatic
    fun bindCurrency(view: Toolbar, clickListener: View.OnClickListener) {
      view.setNavigationOnClickListener {
        view.findNavController().popBackStack()
      }

    }
  }
}