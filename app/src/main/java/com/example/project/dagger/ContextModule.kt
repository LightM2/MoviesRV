package com.example.project.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule {

  @Singleton
  @Provides
  internal fun providesContext(context: Context): Context =
    context

}