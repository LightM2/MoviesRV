package com.example.project

import android.util.Log
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.TreeSet

class MovieViewModel : BaseViewModel() {

  private var movieList = arrayListOf<Value>()

  private var mutableList = MutableLiveData<ArrayList<Value>>()

  var yearsList = TreeSet<Int>()
  var directorsList = TreeSet<String>()
  var genresList = TreeSet<String>()

  val list: LiveData<ArrayList<Value>>
    get() = mutableList

  var visibility = MutableLiveData<Int>()

  fun launchData() {
    visibility.value = ProgressBar.VISIBLE

    viewModelScope.launch {

      val service =
        RetrofitClientInstance.retrofitInstance?.create(GetMoviesService::class.java)

      try {
        val movies = withContext(IO) {
          service?.getAllMovies()?.values
        }
        if (movies != null) {
          mutableList.value = movies

          movies.forEach {
            yearsList.add(it.year)
            directorsList.add(it.director)
            genresList.addAll(it.genre)

            visibility.value = ProgressBar.INVISIBLE
          }
        } else {
          Log.d("Filters", "Exception")
        }

      } catch (e: Throwable) {
        Log.d("Filters", "Exception $e")
      }
    }

    //mutableList.value = movieList
  }
}