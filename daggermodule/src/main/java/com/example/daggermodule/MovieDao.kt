package com.example.daggermodule

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

  @Insert
  suspend fun insert(movies: List<Movie>)

  @Query("DELETE FROM movie")
  suspend fun deleteAllMovies()

  @Query("SELECT * FROM movie ")
  suspend fun getAllMovies(): List<Movie>
}