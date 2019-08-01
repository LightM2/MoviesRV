package com.example.project.dagger

import android.content.Context
import com.example.daggermodule.CustomScope
import com.example.daggermodule.ModelComponent
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [ModelComponent::class])
@CustomScope
interface AppComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance

    fun context(context: Context): Builder

    fun modelComponent(modelComponent: ModelComponent): Builder

    fun build(): AppComponent
  }

  fun inject(viewModel: MovieViewModel)

}
