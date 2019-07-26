package com.example.project.dagger

import android.util.Log
import com.example.project.GetMoviesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
  private val url = "http://demo0216585.mockable.io/"

  @Singleton
  @Provides
  internal fun provideRetrofitInterface(): Retrofit =
    retrofit2
      .Retrofit
      .Builder()
      .baseUrl(url)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

  @Singleton
  @Provides
  internal fun provideGetMoviesService(retrofit: Retrofit): GetMoviesService {
    Log.d("Dagger", "provideGetMoviesService")
    return retrofit.create(GetMoviesService::class.java)
  }

}