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

  private var movies = MutableLiveData<ArrayList<Value>>()

  private val valueList = arrayListOf<Value>()

  //private var sharedFilters = Filters()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    sharedModel = activity?.run {
      ViewModelProviders.of(this).get(SharedViewModel::class.java)
    } ?: throw Exception("Invalid Activity")

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

    sharedModel.yearsList = viewModel.yearsList
    sharedModel.genresList = viewModel.genresList
    sharedModel.directorsList = viewModel.directorsList

    sharedModel.checkedYearFilters.observe(this, Observer { filters ->
      if (filters != null) {
        viewModel.list.value?.forEach { value ->
          filters.filtersList.forEach { filterItem ->
            if (filterItem.state) {
              if (value.year.toString() == filterItem.title) {
                if (!valueList.contains(value)) {
                  valueList.add(value)
                }
                Log.d("Filters", "True ${value.title} ${value.year}")
              }
            }
          }
        }
      }
    })

    sharedModel.checkedDirectorFilters.observe(this, Observer { filters ->
      if (filters != null) {
        viewModel.list.value?.forEach { value ->
          filters.filtersList.forEach { filterItem ->
            if (filterItem.state) {
              if (value.director == filterItem.title) {
                if (!valueList.contains(value)) {
                  valueList.add(value)
                }
                Log.d("Filters", "True ${value.title} ${value.director}")
              }
            }
          }
        }
      }
    })

    sharedModel.checkedGenreFilters.observe(this, Observer { filters ->
      if (filters != null) {
        viewModel.list.value?.forEach { value ->
          filters.filtersList.forEach { filterItem ->
            if (filterItem.state) {
              value.genre.forEach { genre ->
                if (genre == filterItem.title) {
                  if (!valueList.contains(value)) {
                    valueList.add(value)
                  }
                  Log.d("Filters", "True ${value.title} $genre")
                }
              }
            }
          }
        }
      }
    })

    Log.d("Filters", "valueList isNullOrEmpty ${valueList.isNullOrEmpty()}")

    viewModel.visibility.observe(this, Observer {
      moviesPB.visibility = it
      moviesRV.adapter?.notifyItemRangeInserted(0, 1)
    })

    moviesRV.apply {
      layoutManager = LinearLayoutManager(activity)

      if (sharedModel.isNotEmpty()) {
        movies.value?.clear()
        movies.value = valueList
        Log.d("Filters", "movies.value!!.size ${movies.value!!.size}")
        adapter = MovieListAdapter(movies)
      } else {
        adapter = MovieListAdapter(viewModel.list)
      }

      //adapter = MovieListAdapter(movies)

    }
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater?.inflate(R.menu.main_fragment_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

}

