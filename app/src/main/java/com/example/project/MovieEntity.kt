package com.example.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_entity")
data class MovieEntity(
  @PrimaryKey var id: Int,
  //@ColumnInfo(name = "title") var title:String,
  @ColumnInfo(name = "value") var value: Value

)