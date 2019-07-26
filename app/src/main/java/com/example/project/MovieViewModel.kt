package com.example.project

import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dev.auxility.baseadapter.Adapter
import dev.auxility.baseadapter.BaseAdapter
import dev.auxility.baseadapter.item.Item
import kotlinx.coroutines.launch
import java.util.TreeSet

class MovieViewModel : BaseViewModel(), SwipeRefreshLayout.OnRefreshListener {
  override val adapter: Adapter<Item> =
    BaseAdapter(
      listOf(

      )
    )
  private var mutableList = MutableLiveData<List<Movie>>()

  var yearsList = TreeSet<String>()
  var directorsList = TreeSet<String>()
  var genresList = TreeSet<String>()

  val fragmentLiveData: MutableLiveData<(MoviesFragment) -> Unit> = MutableLiveData()

  private fun fragment(block: (MoviesFragment) -> Unit) {
    fragmentLiveData.postValue(block)
  }

  val list: LiveData<List<Movie>>
    get() = mutableList

  var visibility = MutableLiveData<Int>()

  fun launchData(usedSaveData: Boolean = true) {
    //this.context?.let { DaggerAppComponent.builder().context(it).build().inject(it) }
    //DaggerAppComponent.builder().context()

    //CustomApplication.appComponent.inject(this)

    visibility.value = ProgressBar.VISIBLE

    viewModelScope.launch {

      CustomApplication.appComponent.inject(this@MovieViewModel)

      val movies = CustomApplication.movieRepository.allMovies(usedSaveData)

      Log.d("myTag", movies.joinToString(", ") { movie -> movie.toString() })

      mutableList.value = movies

      movies.forEach {
        yearsList.add(it.year.toString())
        directorsList.add(it.director)
        genresList.addAll(it.genre)
        visibility.value = ProgressBar.INVISIBLE
      }
    }
  }

  var progress = ObservableBoolean(false)

  override fun onRefresh() {

    Log.d("mytag", "onRefresh")
    progress.set(true)

    fragment { fragment ->
      Toast.makeText(fragment.context, "Refresh", Toast.LENGTH_SHORT).show()
    }

    progress.set(false)

  }

}