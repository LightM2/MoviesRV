package com.example.daggermodule

import retrofit2.http.GET

interface GetMoviesService {

  @GET("movies")
  suspend fun getAllMovies(): MoviesData

}