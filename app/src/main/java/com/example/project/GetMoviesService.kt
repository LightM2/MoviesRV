package com.example.project

import retrofit2.Call
import retrofit2.http.GET

interface GetMoviesService {

  @GET("movies")
  fun getAllMovies(): Call<MoviesData>

}