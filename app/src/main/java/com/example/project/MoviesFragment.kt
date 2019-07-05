package com.example.project

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.MoviesFragmentBinding
import kotlinx.android.synthetic.main.movies_fragment.moviesPB
import kotlinx.android.synthetic.main.movies_fragment.moviesRV
import kotlinx.android.synthetic.main.movies_fragment.movies_toolbar
import org.greenrobot.eventbus.EventBus

class MoviesFragment : BaseFragment<MoviesFragmentBinding, Movie>() {

  override fun getViewModelClass() = Movie::class.java

  override fun getLayoutId(): Int = R.layout.movies_fragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
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
      viewModel.callWebService()

    }

    EventBus.getDefault().post(viewModel)




    viewModel.visibility.observe(this, Observer {
      moviesPB.visibility = it
      moviesRV.adapter?.notifyItemRangeInserted(0, 1)
    })

    moviesRV.apply {
      layoutManager = LinearLayoutManager(activity)


      adapter = MovieListAdapter(viewModel)

    }

  }


  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater?.inflate(R.menu.main_fragment_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

}

