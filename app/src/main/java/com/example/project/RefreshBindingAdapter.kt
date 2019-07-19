package com.example.project

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class RefreshBindingAdapter {
  companion object RefreshBindingAdapter {
    @BindingAdapter("onRefreshListener")
    @JvmStatic
    fun onRefreshListener(
      view: SwipeRefreshLayout,
      refreshListener: SwipeRefreshLayout.OnRefreshListener
    ) {
      view.setOnRefreshListener(refreshListener)
    }

    @BindingAdapter("isRefreshed")
    @JvmStatic
    fun refreshed(view: SwipeRefreshLayout, progress: ObservableBoolean) {
      view.isRefreshing = progress.get()
    }

  }
}