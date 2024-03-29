package com.example.project

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.daggermodule.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movies_list_rv.view.movieDirectorTV
import kotlinx.android.synthetic.main.movies_list_rv.view.movieGenreTV
import kotlinx.android.synthetic.main.movies_list_rv.view.moviePosterIV
import kotlinx.android.synthetic.main.movies_list_rv.view.movieSummaryTV
import kotlinx.android.synthetic.main.movies_list_rv.view.movieTitleTV
import kotlinx.android.synthetic.main.movies_list_rv.view.movieYearTV

class MovieListAdapter(private var dataList: LiveData<List<Movie>>) :
  RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val itemLayoutView =
      LayoutInflater.from(parent.context).inflate(R.layout.movies_list_rv, parent, false)
    return ViewHolder(itemLayoutView)
  }

  override fun getItemCount() =
    if (dataList.value.isNullOrEmpty()) {
      0
    } else dataList.value!!.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    val data = dataList.value?.get(position)

    if (data != null) {
      holder.bind(
        data.title,
        data.year,
        data.director,
        data.genre,
        data.desription,
        data.image
      )

      Log.d("Filters", "data ${data.title} ${data.year}")
    }
  }

  class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
    private var movieTitle: TextView = itemLayoutView.movieTitleTV
    private var movieYear: TextView = itemLayoutView.movieYearTV
    private var movieDirector: TextView = itemLayoutView.movieDirectorTV
    private var movieGenre: TextView = itemLayoutView.movieGenreTV
    private var movieSummary: TextView = itemLayoutView.movieSummaryTV
    private var moviePoster: ImageView = itemLayoutView.moviePosterIV

    fun bind(
      title: String,
      year: Int,
      director: String,
      genreList: List<String>,
      summary: String,
      poster: String
    ) {
      movieTitle.text = title
      movieYear.text = year.toString()
      movieDirector.text = director
      movieSummary.text = summary
      var genre = ""
      genreList.forEach {
        genre += "$it "
      }
      movieGenre.text = genre
      Picasso.get().load(poster).into(moviePoster)

    }

  }

}