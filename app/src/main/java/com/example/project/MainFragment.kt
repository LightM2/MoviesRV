package com.example.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.main_recycler_view
import kotlinx.android.synthetic.main.main_fragment.main_toolbar

class MainFragment : BaseFragment<MainFragmentBinding, MainViewModel>() {
  override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

  override fun getLayoutId(): Int = R.layout.main_fragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    retainInstance = true

    return inflater.inflate(R.layout.main_fragment, container, false)
  }

  private var yearFilters = Filters()

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    retainInstance = true

    /*if (savedInstanceState != null) {
      yearFilters = savedInstanceState.getSerializable("Years") as Filters
    } else {
      yearFilters = Filters(moviesFiltersYears.mapIndexed { i, _ -> FilterItem(moviesFiltersYears[i]) })
    }*/

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    main_toolbar.inflateMenu(R.menu.main_fragment_menu)

    val genreFilters =
      Filters(moviesFiltersGenres.mapIndexed { i, _ -> FilterItem(moviesFiltersGenres[i]) })

    val directorFilters =
      Filters(moviesFiltersDirectors.mapIndexed { i, _ -> FilterItem(moviesFiltersDirectors[i]) })

    if (savedInstanceState == null) {
      yearFilters =
        Filters(moviesFiltersYears.mapIndexed { i, _ -> FilterItem(moviesFiltersYears[i]) })

      /*genreFilters = Filters(moviesFiltersGenres.mapIndexed{i, _ -> FilterItem(moviesFiltersGenres[i])})
      directorFilters = Filters(moviesFiltersDirectors.mapIndexed{i, _ -> FilterItem(moviesFiltersDirectors[i])})*/

    } else {
      yearFilters = savedInstanceState.getSerializable("Years") as Filters
    }

    main_recycler_view.apply {
      layoutManager = LinearLayoutManager(activity)

      adapter = FiltersAdapter(yearFilters, genreFilters, directorFilters)
    }

    main_toolbar.setOnMenuItemClickListener {
      Log.d("Filters", "title")
      var year = "Years "
      yearFilters.filtersList.forEach {
        if (it.state) {
          year += it.title + " "
          Log.d("Filters", it.title)
        }
      }

      Toast.makeText(context, year, Toast.LENGTH_LONG).show()

      true

    }

  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putSerializable("Years", yearFilters)
  }

  companion object {
    fun newInstance() = MainFragment()
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater?.inflate(R.menu.main_fragment_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  private val moviesFiltersYears = listOf(
    "1954",
    "1957",
    "1972",
    "1974",
    "1975",
    "1990",
    "1993",
    "1994",
    "1997",
    "1998",
    "1999",
    "2001",
    "2002",
    "2003",
    "2008",
    "2014"
  )

  private val moviesFiltersGenres = listOf(
    "Action",
    "Adventure",
    "Biography",
    "Comedy",
    "Crime",
    "Drama",
    "Fantasy",
    "History",
    "Romance",
    "Sci-Fi",
    "War"
  )
  private val moviesFiltersDirectors = listOf(
    "Akira Kurosawa",
    "Christopher Nolan",
    "David Fincher",
    "Fernando Meirelles",
    "Francis Ford Coppola",
    "Frank Darabont",
    "Martin Scrorsese",
    "Milos Forman",
    "Peter Jackson",
    "Quentin Tarantino",
    "Roberto Zemeckis",
    "Roberto Benigni",
    "Sidney Lumet",
    "Steven Spielberg"
  )

}
