package com.example.daggermodule

import android.util.Log
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
  private val url = "http://demo0216585.mockable.io/"

  @Singleton
  @Provides
  @Named("Retrofit")
  fun provideRetrofitInterface(): Retrofit {
    return retrofit2
      .Retrofit
      .Builder()
      .baseUrl(url)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Singleton
  @Provides
  @Named("GetMoviesService")
  fun provideGetMoviesService(): GetMoviesService {
    Log.d("Dagger", "provideGetMoviesService")
    return provideRetrofitInterface().create(GetMoviesService::class.java)
  }


}