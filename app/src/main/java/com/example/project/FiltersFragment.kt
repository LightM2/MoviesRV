package com.example.project

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.main_recycler_view
import kotlinx.android.synthetic.main.main_fragment.main_toolbar

class FiltersFragment : BaseFragment<MainFragmentBinding, MainViewModel>() {
  override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

  override fun getLayoutId(): Int = R.layout.main_fragment

  private lateinit var model: SharedViewModel

  private var yearFilters = Filters()
  private var genreFilters = Filters()
  private var directorFilters = Filters()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    model = activity?.run {
      ViewModelProviders.of(this).get(SharedViewModel::class.java)
    } ?: throw Exception("Invalid Activity")

    yearFilters = if (model.checkedYearFilters.value != null) {
      model.checkedYearFilters.value!!
    } else {
      val yearList = model.yearsList.toList()
      Filters(yearList.mapIndexed { i, _ -> FilterItem(yearList[i].toString()) })
    }

    genreFilters = if (model.checkedGenreFilters.value != null) {
      model.checkedGenreFilters.value!!
    } else {
      val genreList = model.genresList.toList()
      Filters(genreList.mapIndexed { i, _ -> FilterItem(genreList[i]) })
    }

    directorFilters = if (model.checkedDirectorFilters.value != null) {
      model.checkedDirectorFilters.value!!
    } else {
      val directorList = model.directorsList.toList()
      Filters(directorList.mapIndexed { i, _ -> FilterItem(directorList[i]) })
    }

    if (savedInstanceState != null) {
      yearFilters = savedInstanceState.getSerializable("Year") as Filters
      genreFilters = savedInstanceState.getSerializable("Genre") as Filters
      directorFilters = savedInstanceState.getSerializable("Director") as Filters

    }

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    main_toolbar.inflateMenu(R.menu.main_fragment_menu)

    main_recycler_view.apply {
      layoutManager = LinearLayoutManager(activity)

      adapter = FiltersAdapter(yearFilters, genreFilters, directorFilters)
      model.updateList(yearFilters, genreFilters, directorFilters)
    }

    main_toolbar.setOnMenuItemClickListener {
      Log.d("Filters", "title")
      val toast = filtersToast(yearFilters, "Years") +
          "\n${filtersToast(genreFilters, "Genres")}" +
          "\n${filtersToast(directorFilters, "Directors")}"

      Toast.makeText(context, toast, Toast.LENGTH_LONG).show()

      true

    }

  }

  private fun filtersToast(filters: Filters, state: String): String {
    var trueFilters = ""

    filters.filtersList.forEach {
      if (it.state) {
        trueFilters += it.title + " "
        Log.d("Filters", it.title)
      }
    }

    return if (trueFilters.isNotEmpty()) {
      "$state: $trueFilters"
    } else trueFilters
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putSerializable("Year", yearFilters)
    outState.putSerializable("Genre", genreFilters)
    outState.putSerializable("Director", directorFilters)
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater?.inflate(R.menu.main_fragment_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

}
