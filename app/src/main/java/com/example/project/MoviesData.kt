package com.example.project

import com.google.gson.annotations.SerializedName

data class MoviesData(
  @SerializedName("values")
  val values: ArrayList<Value>
)