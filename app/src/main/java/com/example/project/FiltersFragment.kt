package com.example.project

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.main_recycler_view
import kotlinx.android.synthetic.main.main_fragment.main_toolbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FiltersFragment : BaseFragment<MainFragmentBinding, Movie>() {
  override fun getViewModelClass() = Movie::class.java

  override fun getLayoutId(): Int = R.layout.main_fragment

  private var yearFilters = Filters()
  private var movies = Movie()
  private var genreFilters = Filters()
  private var directorFilters = Filters()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    EventBus.getDefault().register(this)

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    main_toolbar.inflateMenu(R.menu.main_fragment_menu)

    if (savedInstanceState == null) {

      movies.list.observe(this, Observer {
        yearFilters =
          Filters(moviesFiltersYears.mapIndexed { i, _ -> FilterItem(it[i].year.toString()) })
      })

      /*yearFilters =
        Filters(moviesFiltersYears.mapIndexed { i, _ -> FilterItem(movies.list.value?.get(i)?.year.toString()) })*/

      genreFilters =
        Filters(moviesFiltersGenres.mapIndexed { i, _ -> FilterItem(moviesFiltersGenres[i]) })
      directorFilters =
        Filters(moviesFiltersDirectors.mapIndexed { i, _ -> FilterItem(moviesFiltersDirectors[i]) })

    } else {
      yearFilters = savedInstanceState.getSerializable("Year") as Filters
      genreFilters = savedInstanceState.getSerializable("Genre") as Filters
      directorFilters = savedInstanceState.getSerializable("Director") as Filters

    }

    main_recycler_view.apply {
      layoutManager = LinearLayoutManager(activity)

      adapter = FiltersAdapter(yearFilters, genreFilters, directorFilters)
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

  override fun onDestroy() {
    super.onDestroy()
    EventBus.getDefault().unregister(this)
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onViewModel(movieList: Movie) {
    movies = movieList
    Log.d("Filters", "onViewModel FF ")
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
