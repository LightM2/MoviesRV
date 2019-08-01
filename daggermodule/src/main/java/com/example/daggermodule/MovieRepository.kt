package com.example.daggermodule

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
  private var movieDao: MovieDao,
  private var getMoviesService: GetMoviesService
) {

  suspend fun allMovies(allowedCached: Boolean): List<Movie> = if (allowedCached) {
    getMoviesFromDB().let { localMovies ->
      if (localMovies.isNullOrEmpty()) {
        getMoviesFromNetwork()
      } else {
        localMovies
      }
    }
  } else {
    getMoviesFromNetwork()
  }

  private suspend fun getMoviesFromNetwork(): List<Movie> {
    Log.d("Dagger", "getMoviesFromNetwork")
    val movies = getMoviesService.getAllMovies().movies
    movieDao.deleteAllMovies()
    movieDao.insert(movies)
    return movies
  }

  private suspend fun getMoviesFromDB(): List<Movie> = movieDao.getAllMovies()

}