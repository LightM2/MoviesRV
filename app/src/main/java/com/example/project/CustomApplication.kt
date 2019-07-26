package com.example.project

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.project.dagger.AppComponent
import com.example.project.dagger.DaggerAppComponent
import com.example.project.dagger.MovieRepository
import retrofit2.converter.gson.GsonConverterFactory

class CustomApplication : Application() {

  //@Inject


  private val url = "http://demo0216585.mockable.io/"
  override fun onCreate() {
    super.onCreate()
    val movieDao = Room.databaseBuilder(this, MovieDatabase::class.java, "db").build().movieDao()

    val getMoviesService = retrofit2
      .Retrofit
      .Builder()
      .baseUrl(url)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(GetMoviesService::class.java)

    movieRepository = MovieRepository(movieDao, getMoviesService)

    appComponent = DaggerAppComponent.builder().context(this).build()
    Log.d("Dagger", "CustomApplication")

  }

  companion object Repository {
    lateinit var appComponent: AppComponent

    lateinit var movieRepository: MovieRepository

  }
}