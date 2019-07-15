package com.example.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MovieRepository(
  private var movieDao: MovieDao,
  private var getMoviesService: GetMoviesService
) {

  private var scope = CoroutineScope(IO + SupervisorJob())

  fun allMovies() = scope.launch {

  }

}