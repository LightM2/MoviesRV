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

class MoviesFragment : BaseFragment<MoviesFragmentBinding, Movie>() {

  override fun getViewModelClass() = Movie::class.java

  override fun getLayoutId(): Int = R.layout.movies_fragment

  private lateinit var sharedModel: SharedViewModel

  private var filteredMovies = MutableLiveData<ArrayList<Value>>()

  private val valueList = arrayListOf<Value>()

  private var sharedFilters = Filters()


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

    viewModel.callWebService()


    sharedModel.yearsList = viewModel.yearsList
    sharedModel.genresList = viewModel.genresList
    sharedModel.directorsList = viewModel.directorsList

    if (sharedModel.checkedYearFilters.value != null) {
      sharedFilters = sharedModel.checkedYearFilters.value!!

      viewModel.list.value?.forEach { value ->
        sharedFilters.filtersList.forEach { filterItem ->
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

    Log.d("Filters", "valueList ${valueList.isNullOrEmpty()}")

    viewModel.visibility.observe(this, Observer {
      moviesPB.visibility = it
      moviesRV.adapter?.notifyItemRangeInserted(0, 1)
    })

    moviesRV.apply {
      layoutManager = LinearLayoutManager(activity)

      if (filteredMovies.value.isNullOrEmpty() && !valueList.isNullOrEmpty()) {
        filteredMovies.value = valueList
        Log.d("Filters", "valueList ${filteredMovies.value!!.size}")
        adapter = MovieListAdapter(filteredMovies)
      } else adapter = MovieListAdapter(viewModel.list)


    }
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater?.inflate(R.menu.main_fragment_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

}

