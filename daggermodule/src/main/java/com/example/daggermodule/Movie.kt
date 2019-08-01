package com.example.daggermodule

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class Movie(
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0,
  @SerializedName("desription")
  val desription: String,
  val director: String,
  val genre: List<String>,
  val image: String,
  val title: String,
  val year: Int
)