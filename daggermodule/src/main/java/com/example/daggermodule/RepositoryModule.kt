package com.example.daggermodule

import android.util.Log
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class RepositoryModule {

  @Singleton
  @Provides
  fun provideRepository(movieDao: MovieDao, getMoviesService: GetMoviesService): MovieRepository {
    Log.d("Dagger", "provideRepository")
    return MovieRepository(movieDao, getMoviesService)
  }

}