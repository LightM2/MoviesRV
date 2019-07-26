package com.example.project.dagger

import android.util.Log
import com.example.project.GetMoviesService
import com.example.project.MovieDao
import dagger.Module
import dagger.Provides

@Module
internal class AppModule {

  @CustomScope
  @Provides
  fun provideRepository(movieDao: MovieDao, getMoviesService: GetMoviesService): MovieRepository {
    Log.d("Dagger", "provideRepository")
    return MovieRepository(movieDao, getMoviesService)
  }

}