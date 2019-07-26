package com.example.project.dagger

import android.content.Context
import com.example.project.MovieViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
@CustomScope
interface AppComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun context(context: Context): Builder

    fun build(): AppComponent
  }

  fun inject(viewModel: MovieViewModel)

  fun modelComponentBuilder(): ModelComponent.Builder

}
