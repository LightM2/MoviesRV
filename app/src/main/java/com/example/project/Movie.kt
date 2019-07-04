package com.example.project

import android.util.Log
import android.widget.ProgressBar
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class Movie : BaseViewModel(), Serializable {

  @get: Bindable var movieList = listOf<Value>()
    set(value) {
      if (field != value) {
        field = value
        notifyPropertyChanged(BR.movieList)
      }
    }

  var list = MutableLiveData<List<Value>>()
  var visibility = MutableLiveData<Int>()

  fun callWebService() {
    visibility.value = ProgressBar.VISIBLE
    val service =
      RetrofitClientInstance.retrofitInstance?.create(GetMoviesService::class.java)
    val call = service?.getAllMovies()
    call?.enqueue(object : Callback<MoviesData> {

      override fun onResponse(call: Call<MoviesData>, response: Response<MoviesData>) {
        val body = response.body()
        if (body?.values == null) {

        } else {
          movieList.forEach {

            Log.d("Filters", it.title)
          }
          movieList = body.values

          list.value = movieList

        }

        visibility.value = ProgressBar.INVISIBLE

      }

      override fun onFailure(call: Call<MoviesData>, t: Throwable) {
        visibility.value = ProgressBar.INVISIBLE

      }

    })
  }

}