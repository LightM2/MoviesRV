package com.example.project

import android.util.Log
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Movie : BaseViewModel() {

  private var movieList = listOf<Value>()

  private var mutableList = MutableLiveData<List<Value>>()
  val list: LiveData<List<Value>>
    get() = mutableList

  val size
    get() = movieList.size


  var visibility = MutableLiveData<Int>()


  fun callWebService() {
    visibility.value = ProgressBar.VISIBLE
    val service =
      RetrofitClientInstance.retrofitInstance?.create(GetMoviesService::class.java)
    val call = service?.getAllMovies()
    call?.enqueue(object : Callback<MoviesData> {

      override fun onResponse(call: Call<MoviesData>, response: Response<MoviesData>) {
        val body = response.body()
        if (body?.values != null) {
          movieList.forEach {

            Log.d("Filters", it.title)
          }
          movieList = body.values

          mutableList.value = movieList
        }

        visibility.value = ProgressBar.INVISIBLE

      }

      override fun onFailure(call: Call<MoviesData>, t: Throwable) {
        visibility.value = ProgressBar.INVISIBLE

      }

    })
  }

  fun onClick() {
    Log.d("Filters", "Click ToolBar")
    //Navigation.createNavigateOnClickListener(R.id.toFilters)
  }

}