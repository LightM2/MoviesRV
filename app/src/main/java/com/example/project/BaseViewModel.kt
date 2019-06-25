package com.example.project

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), Observable {

  var fragment: Fragment? = null

  @Transient
  private var mCallbacks: PropertyChangeRegistry? = null

  fun BaseObservable() {}

  override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
    synchronized(this) {
      if (mCallbacks == null) {
        mCallbacks = PropertyChangeRegistry()
      }
    }
    mCallbacks!!.add(callback)
  }

  override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
    synchronized(this) {
      if (mCallbacks == null) {
        return
      }
    }
    mCallbacks!!.remove(callback)
  }

  /**
   * Notifies listeners that all properties of this instance have changed.
   */
  fun notifyChange() {
    synchronized(this) {
      if (mCallbacks == null) {
        return
      }
    }
    mCallbacks!!.notifyCallbacks(this, 0, null)
  }

  /**
   * Notifies listeners that a specific property has changed. The getter for the property
   * that changes should be marked with [Bindable] to generate a field in
   * `BR` to be used as `fieldId`.
   *
   * @param fieldId The generated BR id for the Bindable field.
   */
  fun notifyPropertyChanged(fieldId: Int) {
    synchronized(this) {
      if (mCallbacks == null) {
        return
      }
    }
    mCallbacks!!.notifyCallbacks(this, fieldId, null)
  }
}