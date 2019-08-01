package com.example.project.dagger

import android.content.Context
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.daggermodule.GetMoviesService
import com.example.daggermodule.Movie
import com.example.daggermodule.MovieDao
import com.example.daggermodule.MovieRepository
import com.example.project.BaseViewModel
import com.example.project.MoviesFragment
import dev.auxility.baseadapter.Adapter
import dev.auxility.baseadapter.BaseAdapter
import dev.auxility.baseadapter.item.Item
import kotlinx.coroutines.launch
import java.util.TreeSet
import javax.inject.Inject
import javax.inject.Named

class MovieViewModel : BaseViewModel(), SwipeRefreshLayout.OnRefreshListener {

  //@Inject lateinit var movieRepository: MovieRepository

  @field:[Inject Named("GetMoviesService")]
  lateinit var getMoviesService: GetMoviesService

  @field:[Inject Named("MovieDao")]
  lateinit var movieDao: MovieDao

  @Inject
  lateinit var context: Context

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

  init {
    CustomApplication.appComponent.inject(this)
  }

  private fun fragment(block: (MoviesFragment) -> Unit) {
    fragmentLiveData.postValue(block)
  }

  val list: LiveData<List<Movie>>
    get() = mutableList

  var visibility = MutableLiveData<Int>()

  fun launchData(usedSaveData: Boolean = true) {

    visibility.value = ProgressBar.VISIBLE

    viewModelScope.launch {

      val movieRepository = MovieRepository(movieDao, getMoviesService)

      val movies = movieRepository.allMovies(usedSaveData)

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