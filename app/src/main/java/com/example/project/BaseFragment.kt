package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

  lateinit var binding: T

  lateinit var viewModel: V

  @LayoutRes abstract fun getLayoutId(): Int

  abstract fun getViewModelClass(): Class<V>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(getViewModelClass())
    viewModel.fragment = this
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
    binding.setVariable(BR.viewModel, viewModel)
    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    viewModel.fragment = null
  }
}