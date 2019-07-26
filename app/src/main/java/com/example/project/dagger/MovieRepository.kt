package com.example.project.dagger

import android.util.Log
import com.example.project.GetMoviesService
import com.example.project.Movie
import com.example.project.MovieDao
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
    Log.d("mytag", "Retrofit")
    val movies = getMoviesService.getAllMovies().movies
    movieDao.deleteAllMovies()
    movieDao.insert(movies)
    return movies
  }

  private suspend fun getMoviesFromDB(): List<Movie> = movieDao.getAllMovies()

}