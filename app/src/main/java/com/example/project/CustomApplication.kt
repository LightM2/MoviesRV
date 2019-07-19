package com.example.project

import android.app.Application
import androidx.room.Room
import retrofit2.converter.gson.GsonConverterFactory

class CustomApplication : Application() {
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

  }

  companion object Repository {
    lateinit var movieRepository: MovieRepository
  }
}