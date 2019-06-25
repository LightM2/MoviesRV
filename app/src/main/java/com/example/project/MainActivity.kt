package com.example.project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val window = this.window
    window.statusBarColor = this.resources.getColor(R.color.statusBarColor)

    val mainFragment = MainFragment()
    val manager = supportFragmentManager
    val transaction = manager.beginTransaction()
    transaction.replace(R.id.fragmentFrame, mainFragment)
    transaction.addToBackStack(null)
    transaction.commit()

  }
}
