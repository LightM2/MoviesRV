package com.example.daggermodule

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(internal var context: Context) {

  @Singleton
  @Provides
  fun provideContext(): Context {
    return context
  }
}