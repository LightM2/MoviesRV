package com.example.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.movies_fragment.moviesPB
import kotlinx.android.synthetic.main.movies_fragment.movies_recycler_view
import kotlinx.android.synthetic.main.movies_fragment.movies_toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesFragment : BaseFragment<MainFragmentBinding, MainViewModel>() {

  private var movies = mutableListOf<Value>()

  override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

  override fun getLayoutId(): Int = R.layout.movies_fragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {


    return inflater.inflate(R.layout.movies_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    moviesPB.visibility = ProgressBar.VISIBLE
    callWebService()

    movies_toolbar.inflateMenu(R.menu.main_fragment_menu)

    movies_toolbar.setOnMenuItemClickListener {
      Log.d("Filters", "Click")
      view.findNavController().navigate(R.id.toFilters)

      true

    }

    movies_recycler_view.apply {
      layoutManager = LinearLayoutManager(activity)

      adapter = MovieListAdapter(movies)

    }

  }

  private fun callWebService() {
    val service =
      RetrofitClientInstance.retrofitInstance?.create(GetMoviesService::class.java) //GetMoviesService.create()
    val call = service?.getAllMovies()
    call?.enqueue(object : Callback<MoviesData> {

      override fun onResponse(call: Call<MoviesData>, response: Response<MoviesData>) {
        val body = response.body()
        if (body?.values == null) {
          Toast.makeText(context, "Error reading JSON", Toast.LENGTH_LONG).show()
        } else {
          //movies = body.values
          body.values.forEach {
            movies.add(it)
          }

          movies.forEach {

            Log.d("Filters", it.title)
          }
          movies_recycler_view.adapter?.notifyItemRangeInserted(0, movies.size)

        }

        moviesPB.visibility = ProgressBar.INVISIBLE

      }

      override fun onFailure(call: Call<MoviesData>, t: Throwable) {
        Toast.makeText(context, "Error reading JSON", Toast.LENGTH_LONG).show()
        moviesPB.visibility = ProgressBar.INVISIBLE
      }

    })
  }


  companion object {
    fun newInstance() = MoviesFragment()
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater?.inflate(R.menu.main_fragment_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }
}