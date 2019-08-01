package com.example.project.dagger

import android.app.Application
import android.util.Log
import com.example.daggermodule.ContextModule
import com.example.daggermodule.DBModule
import com.example.daggermodule.DaggerModelComponent
import com.example.daggermodule.ModelComponent
import com.example.daggermodule.NetworkModule

class CustomApplication : Application() {

  //private val url = "http://demo0216585.mockable.io/"
  override fun onCreate() {
    super.onCreate()

    /*val movieDao = Room.databaseBuilder(this, MovieDatabase::class.java, "db").build().movieDao()

    val getMoviesService = retrofit2
      .Retrofit
      .Builder()
      .baseUrl(url)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(GetMoviesService::class.java)*/

    //movieRepository = MovieRepository(movieDao, getMoviesService)

    modelComponent = DaggerModelComponent.builder().context(this).contextModule(ContextModule(this))
      .dbModule(DBModule(this))
      .networkModule(NetworkModule()).build()

    appComponent = DaggerAppComponent.builder().context(this).modelComponent(modelComponent).build()

    /*modelComponent =
    DaggerModelComponent.builder().appComponent(appComponent).dBModule(
      DBModule(this)).build()*/


    Log.d("Dagger", "CustomApplication")

  }

  companion object Repository {

    lateinit var appComponent: AppComponent

    lateinit var modelComponent: ModelComponent

    //lateinit var movieRepository: MovieRepository

  }
}