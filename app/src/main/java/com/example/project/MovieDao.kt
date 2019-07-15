package com.example.project

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

  @Insert
  fun insert(note: MovieEntity)

  @Query("DELETE FROM movie_entity")
  fun deleteAllMovies()

  @Query("SELECT * FROM movie_entity ")
  suspend fun getAllMovies(): List<MovieEntity>
}