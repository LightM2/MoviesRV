package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_view.view.checkBoxRV
import kotlinx.android.synthetic.main.list_view.view.textViewRV
import kotlinx.android.synthetic.main.title_view.view.titleViewRV

class FiltersAdapter(
  private var moviesFiltersYears: List<FilterItem>,
  private var moviesFiltersGenres: List<FilterItem>,
  private var moviesFiltersDirectors: List<FilterItem>
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
    return moviesFiltersYears.size + moviesFiltersGenres.size + moviesFiltersDirectors.size + 3
  }

  override fun getItemViewType(position: Int): Int {
    return when (position) {
      0, moviesFiltersYears.size + 1, moviesFiltersYears.size + moviesFiltersGenres.size + 2 -> 0
      else -> 1
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is TitleViewHolder -> {
        if (getItemViewType(position) == 0) {
          holder.bind(when (position) {
            0 -> "Year"
            moviesFiltersYears.size + 1 -> "Genres"
            moviesFiltersYears.size + moviesFiltersGenres.size + 2 -> "Directors"
            else -> ""
          })
        }
      }
      is ViewHolder -> {
        val positionRV: Int
        when {
          position <= moviesFiltersYears.size -> {
            positionRV = position-1
            holder.bind(moviesFiltersYears[positionRV])
            if (moviesFiltersYears[positionRV].title == "All" && moviesFiltersYears[positionRV].state) {
              allFilterState(moviesFiltersYears)
            }
          }
          position <= moviesFiltersYears.size + moviesFiltersGenres.size + 1 -> {
            positionRV = position - moviesFiltersYears.size - 2
            holder.bind(moviesFiltersGenres[positionRV])
          }
          else -> {
            positionRV = position - moviesFiltersYears.size - moviesFiltersGenres.size - 3
            holder.bind(moviesFiltersDirectors[positionRV])
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

  inner class ViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(filterItem: FilterItem) {

      itemView.textViewRV.text = filterItem.title

      itemView.checkBoxRV.isChecked = filterItem.state


      itemView.checkBoxRV.setOnClickListener {
        filterItem.state = !filterItem.state
        /*moviesFiltersYears[position].state = filterItem.state
        if (filterItem.title=="All"){
          if (filterItem.state){
            for (i in 1 until moviesFiltersYears.size){
              moviesFiltersYears[i].state = true
            }
          }
          if (filter.filtersState[0] and !filter.filtersState[position]){
              filter.filtersState[0] = false
            }
            Log.d("Movies", "${filter.filtersState[position]}")
        }*/

      }


      binding.executePendingBindings()
    }

  }

  fun allFilterState(list: List<FilterItem>) {
    for (i in 0 until list.size) {
      list[i].state = true
    }
  }



}