package com.example.project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FiltersActivity : AppCompatActivity() {

  lateinit var mainFragment: MainFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    /*val manager = supportFragmentManager
    val transaction = manager.beginTransaction()
    mainFragment = MainFragment.newInstance() //Filters(mutableListOf(FilterItem("2000")))

    if (savedInstanceState == null){
      transaction.replace(R.id.fragmentFrame, mainFragment)
    }

    transaction.addToBackStack(null)
    transaction.commit()*/


  }

}
