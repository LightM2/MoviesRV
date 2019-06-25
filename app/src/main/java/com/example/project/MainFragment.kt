package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.main_recycler_view

class MainFragment : BaseFragment<MainFragmentBinding, MainViewModel>() {
  override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

  override fun getLayoutId(): Int = R.layout.main_fragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onCreateView(inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?): View? =
    inflater.inflate(R.layout.main_fragment, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    main_recycler_view.apply {
      layoutManager = LinearLayoutManager(activity)
      val yearFilters = Filters()
      yearFilters.addElements(moviesFiltersYears)
      val genreFilters = Filters()
      genreFilters.addElements(moviesFiltersGenres)
      val directorFilters = Filters()
      directorFilters.addElements(moviesFiltersDirectors)

      adapter = FiltersAdapter(yearFilters, genreFilters, directorFilters)
    }

  }
  companion object {
    fun newInstance() = MainFragment()
  }

  private val moviesFiltersYears = listOf(
    "All",
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
    "All",
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
    "All",
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
