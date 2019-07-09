package com.example.project

import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.TreeSet

class Movie : BaseViewModel() {

  private var movieList = arrayListOf<Value>()

  private var mutableList = MutableLiveData<ArrayList<Value>>()

  var yearsList = TreeSet<Int>()
  var directorsList = TreeSet<String>()
  var genresList = TreeSet<String>()

  val list: LiveData<ArrayList<Value>>
    get() = mutableList

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
          movieList = body.values

          movieList.forEach {
            yearsList.add(it.year)
            directorsList.add(it.director)
            genresList.addAll(it.genre)
          }

          mutableList.value = movieList
        }

        visibility.value = ProgressBar.INVISIBLE

      }

      override fun onFailure(call: Call<MoviesData>, t: Throwable) {
        visibility.value = ProgressBar.INVISIBLE

      }

    })
  }

}