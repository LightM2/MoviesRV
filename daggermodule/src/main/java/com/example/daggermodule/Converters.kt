package com.example.daggermodule

import androidx.room.TypeConverter

object Converters {

  @TypeConverter
  @JvmStatic
  fun fromListOfStrings(list: List<String>): String {
    return list.joinToString(",")
  }

  @TypeConverter
  @JvmStatic
  fun toListOfStrings(data: String): List<String> {
    return data.split(",")
  }

}