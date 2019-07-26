package com.example.project.dagger

import android.content.Context
import androidx.room.Room
import com.example.project.MovieDao
import com.example.project.MovieDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {

  @Singleton
  @Provides
  internal fun provideMovieDatabase(context: Context): MovieDatabase =
    Room.databaseBuilder(context, MovieDatabase::class.java, "db").build()

  @Singleton
  @Provides
  internal fun provideMovie(db: MovieDatabase): MovieDao =
    db.movieDao()

}