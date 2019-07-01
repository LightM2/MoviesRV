package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.title_view.view.titleViewRV

class FiltersAdapter(
  private var moviesFiltersYears: Filters,
  private var moviesFiltersGenres: Filters,
  private var moviesFiltersDirectors: Filters
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    when (viewType) {
      0 -> {
        return TitleViewHolder(
          LayoutInflater.from(parent.context).inflate(
            R.layout.title_view,
            parent,
            false
          )
        )
      }
      else -> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater,R.layout.list_view, parent, false)

        return ViewHolder(binding)
      }
    }
  }

  override fun getItemCount(): Int {
    return moviesFiltersYears.filtersList.size + moviesFiltersGenres.filtersList.size + moviesFiltersDirectors.filtersList.size + 6//+3
  }

  override fun getItemViewType(position: Int): Int {
    return when (position) {
      0, moviesFiltersYears.filtersList.size + 2, moviesFiltersYears.filtersList.size + moviesFiltersGenres.filtersList.size + 4 -> 0//0, +1, +2
      else -> 1
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    when (holder) {

      is TitleViewHolder -> {
        if (getItemViewType(position) == 0) {
          holder.bind(when (position) {
            0 -> "Year"
            moviesFiltersYears.filtersList.size + 2 -> "Genres"//+1
            moviesFiltersYears.filtersList.size + moviesFiltersGenres.filtersList.size + 4 -> "Directors"//+2
            else -> ""
          })
        }
      }

      is ViewHolder -> {
        val positionRV: Int

        when {
          position <= moviesFiltersYears.filtersList.size + 1 -> {//+1
            positionRV = position - 2
            if (positionRV == -1) {
              holder.bind(moviesFiltersYears)
            } else {
              holder.bind(moviesFiltersYears.filtersList[positionRV])
            }
          }

          position <= moviesFiltersYears.filtersList.size + moviesFiltersGenres.filtersList.size + 3 -> {//+2
            positionRV = position - moviesFiltersYears.filtersList.size - 4
            if (positionRV == -1) {
              holder.bind(moviesFiltersGenres)
            } else holder.bind(moviesFiltersGenres.filtersList[positionRV])
          }

          else -> {
            positionRV =
              position - moviesFiltersYears.filtersList.size - moviesFiltersGenres.filtersList.size - 6
            if (positionRV == -1) {
              holder.bind(moviesFiltersDirectors)
            } else holder.bind(moviesFiltersDirectors.filtersList[positionRV])
          }
        }
      }
    }
  }

  class TitleViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
    private var filtersTitle: TextView = itemLayoutView.titleViewRV
    fun bind(title: String) {
      filtersTitle.text = title
    }
  }

  class ViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(filterItem: FilterItem) {
      binding.setVariable(BR.item, filterItem)
      /*if (filterItem.title == "All" && filterItem.state){
        moviesFiltersYears.allFilterStateTrue()
      }*/
      binding.executePendingBindings()
    }

  }


}