package com.example.daggermodule

import com.google.gson.annotations.SerializedName

data class MoviesData(
  @SerializedName("values")
  val movies: List<Movie>
)