package com.example.project

import com.google.gson.annotations.SerializedName

data class Value(
  @SerializedName("desription")
  val desription: String,

  @SerializedName("director")
  val director: String,

  @SerializedName("genre")
  val genre: List<String>,

  @SerializedName("image")
  val image: String,

  @SerializedName("title")
  val title: String,

  @SerializedName("year")
  val year: Int
)