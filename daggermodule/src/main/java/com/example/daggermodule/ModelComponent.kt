package com.example.daggermodule

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Component(modules = [ContextModule::class, DBModule::class, NetworkModule::class])
@Singleton
interface ModelComponent {

  @Named("MovieDao") fun providesDao(): MovieDao

  @Named("GetMoviesService")
  fun provideGetMoviesService(): GetMoviesService

  //fun provideContext():Context

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun context(context: Context): Builder

    fun contextModule(contextModule: ContextModule): Builder
    fun dbModule(dbModule: DBModule): Builder
    fun networkModule(networkModule: NetworkModule): Builder
    fun build(): ModelComponent
  }

}

