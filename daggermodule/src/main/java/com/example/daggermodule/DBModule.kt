package com.example.daggermodule

import android.content.Context
import android.util.Log
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DBModule(var context: Context) {

  @Singleton
  @Provides
  fun provideMovieDatabase(): MovieDatabase {
    Log.d("Dagger", "provideMovieDatabase")
    return Room.databaseBuilder(context, MovieDatabase::class.java, "db").build()
  }

  @Singleton
  @Provides
  @Named("MovieDao")
  fun provideMovie(): MovieDao =
    provideMovieDatabase().movieDao()

}