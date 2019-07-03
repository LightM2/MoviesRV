package com.example.project

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {

  private var retrofit: Retrofit? = null
  private const val BASE_URL = "http://demo0216585.mockable.io/"

  val retrofitInstance: Retrofit?
    get() {
      if (retrofit == null) {
        retrofit = retrofit2.Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
      }
      return retrofit
    }

}