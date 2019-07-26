package com.example.project

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.MoviesFragmentBinding
import kotlinx.android.synthetic.main.movies_fragment.moviesPB
import kotlinx.android.synthetic.main.movies_fragment.moviesRV
import kotlinx.android.synthetic.main.movies_fragment.movies_toolbar

class MoviesFragment : BaseFragment<MoviesFragmentBinding, MovieViewModel>() {

  override fun getViewModelClass() = MovieViewModel::class.java

  override fun getLayoutId(): Int = R.layout.movies_fragment

  private lateinit var sharedModel: SharedViewModel

  private var movies = MutableLiveData<List<Movie>>()

  private val valueList = mutableListOf<Movie>()



  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    sharedModel = activity?.run {
      ViewModelProviders.of(this).get(SharedViewModel::class.java)
    } ?: throw Exception("Invalid Activity")

    viewModel.fragmentLiveData.observe(this, Observer { function ->
      function(this)
      sharedModel.deleteAllFilters()
      movies.value = null
      viewModel.launchData(false)
      moviesRV.adapter?.notifyDataSetChanged()
      Log.d("Filters", "Refresh")

    })

    //this.context?.let { DaggerAppComponent.builder().context(it).build().inject(it) }


  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    movies_toolbar.inflateMenu(R.menu.main_fragment_menu)

    movies_toolbar.setOnMenuItemClickListener {
      Log.d("Filters", "Click")
      view.findNavController().navigate(R.id.toFilters)
      true

    }

    if (viewModel.list.value.isNullOrEmpty()) {
      viewModel.launchData()
    }

    viewModel.list.observe(this, Observer {
      if (!sharedModel.isNotEmpty()) {
        movies.value = viewModel.list.value
      }

    })

    sharedModel.filters = mapOf(
      "directors" to viewModel.directorsList.toList(),
      "genres" to viewModel.genresList.toList(),
      "years" to viewModel.yearsList.toList()
    )


    valueList.removeAll(valueList)

    filteredMovie(sharedModel.checkedYearFilters, FiltersType.YEAR)
    filteredMovie(sharedModel.checkedDirectorFilters, FiltersType.DIRECTOR)
    filteredMovie(sharedModel.checkedGenreFilters, FiltersType.GENRE)

    Log.d("Filters", "valueList isNullOrEmpty ${valueList.isNullOrEmpty()}")

    viewModel.visibility.observe(this, Observer {
      moviesPB.visibility = it
      moviesRV.adapter?.notifyItemRangeInserted(0, 1)
    })

    moviesRV.apply {
      layoutManager = LinearLayoutManager(activity)

      if (sharedModel.isNotEmpty()) {
        movies.value = null
        movies.value = valueList
        Log.d("Filters", "movies.movie!!.size ${movies.value!!.size}")

      }

      adapter = MovieListAdapter(movies)

    }
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater?.inflate(R.menu.main_fragment_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  private fun filteredMovie(mutableLiveData: MutableLiveData<Filters>, filtersType: FiltersType) {
    mutableLiveData.observe(this, Observer { filters ->
      if (filters != null) {
        viewModel.list.value?.forEach { value ->
          filters.filtersList.forEach { filterItem ->
            if (filterItem.state) {
              when (filtersType) {
                FiltersType.YEAR -> sort(
                  listOf(value.year.toString()),
                  value,
                  filterItem
                )
                FiltersType.GENRE -> sort(
                  value.genre,
                  value,
                  filterItem
                )
                FiltersType.DIRECTOR -> sort(
                  listOf(value.director),
                  value,
                  filterItem
                )
              }
            }
          }
        }
      }
    })
  }

  private fun sort(list: List<String>, movie: Movie, filterItem: FilterItem) {
    list.forEach { listItem ->
      if (listItem == filterItem.title) {
        if (!valueList.contains(movie)) {
          valueList.add(movie)
        }
        Log.d("Filters", "True ${movie.title} $listItem")

      }
    }
  }

  enum class FiltersType {
    YEAR,
    GENRE,
    DIRECTOR
  }

}

