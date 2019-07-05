package com.example.project

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

  lateinit var mainFragment: FiltersFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

  }

  override fun onStart() {
    super.onStart()

    EventBus.getDefault().register(this)
  }

  override fun onStop() {
    super.onStop()

    EventBus.getDefault().unregister(this)
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onViewModel(movieList: Movie) {
    Log.d("Filters", "onViewModel")
  }

}
