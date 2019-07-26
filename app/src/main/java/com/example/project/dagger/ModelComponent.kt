package com.example.project.dagger

import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent(modules = [ContextModule::class, DBModule::class, NetworkModule::class])
@Singleton
interface ModelComponent {

  @Subcomponent.Builder
  interface Builder {
    fun contextModule(contextModule: ContextModule): Builder
    fun dbModule(dbModule: DBModule): Builder
    fun networkModule(networkModule: NetworkModule): Builder
    fun build(): ModelComponent
  }

}

