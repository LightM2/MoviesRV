package com.example.project

import android.app.Application

class CustomApplication : Application() {
  lateinit var movieDao: MovieDao
  lateinit var getMoviesService: GetMoviesService
  override fun onCreate() {
    super.onCreate()
    movieDao = MovieDatabase.getDatabase(this).movieDao()
    getMoviesService =
      RetrofitClientInstance.retrofitInstance?.create(GetMoviesService::class.java)!!

  }

  init {
    movieRepository = MovieRepository(movieDao, getMoviesService)
  }

  companion object Repository {
    lateinit var movieRepository: MovieRepository
  }
}