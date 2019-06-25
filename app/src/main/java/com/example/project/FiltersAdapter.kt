package com.example.project

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_view.view.checkBoxRV
import kotlinx.android.synthetic.main.list_view.view.textViewRV
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

        return ViewHolde(binding)
      }
    }
  }

  override fun getItemCount(): Int {
    return moviesFiltersYears.size() + moviesFiltersGenres.size() + moviesFiltersDirectors.size() + 3
  }

  override fun getItemViewType(position: Int): Int {
    return when (position) {
      0, moviesFiltersYears.size()+1, moviesFiltersYears.size() + moviesFiltersGenres.size()+2 -> 0
      else -> 1
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is TitleViewHolder -> {
        if (getItemViewType(position) == 0) {
          holder.bind(when (position) {
            0 -> "Year"
            moviesFiltersYears.size()+1 -> "Genres"
            moviesFiltersYears.size() + moviesFiltersGenres.size()+2 -> "Directors"
            else -> ""
          })
        }
      }
      is ViewHolder -> {
        val positionRV: Int
        when {
          position <= moviesFiltersYears.size() -> {
            positionRV = position-1
            holder.bind(positionRV,moviesFiltersYears)
          }
          position <= moviesFiltersYears.size() + moviesFiltersGenres.size()+1 -> {
            positionRV = position - moviesFiltersYears.size() -2
            holder.bind(positionRV,moviesFiltersGenres)
          }
          else -> {
            positionRV = position - moviesFiltersYears.size() - moviesFiltersGenres.size()-3
            holder.bind(positionRV,moviesFiltersDirectors)
          }
        }
      }
      is ViewHolde ->{
        val positionRV: Int
        when {
          position <= moviesFiltersYears.size() -> {
            positionRV = position-1
            holder.bind(positionRV,moviesFiltersYears)
          }
          position <= moviesFiltersYears.size() + moviesFiltersGenres.size()+1 -> {
            positionRV = position - moviesFiltersYears.size() -2
            holder.bind(positionRV,moviesFiltersGenres)
          }
          else -> {
            positionRV = position - moviesFiltersYears.size() - moviesFiltersGenres.size()-3
            holder.bind(positionRV,moviesFiltersDirectors)
          }
        }
      }
    }
  }

  class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView){
    private var filtersButton: CheckBox = itemLayoutView.checkBoxRV
    private var filtersText: TextView = itemLayoutView.textViewRV

    fun bind(position: Int, filter: Filters){
      filtersText.text = filter.filtersName[position]
      filtersButton.isChecked = filter.filtersState[position]
      filtersButton.setOnClickListener {
        filter.newFilterState(position, !filter.filtersState[position])
        /*filter.checkAllFiltersOneState()
        if (filter.filtersName[position]=="All"){
          if (filter.filtersState[position]){
            filter.allFilterState(true)
          }
        }*/

        Log.d("Movies", "${filter.filtersState[position]}")
      }


    }

  }

  class TitleViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
    private var filtersTitle: TextView = itemLayoutView.titleViewRV
    fun bind(title: String) {
      filtersTitle.text = title
    }
  }

  inner class ViewHolde(private val binding: ViewDataBinding) :RecyclerView.ViewHolder(binding.root){
    fun bind(position: Int, filter: Filters){

      itemView.textViewRV.text = filter.filtersName[position]
      //binding.setVariable(BR.filterState, true)
      //filter.filterState = true

      itemView.checkBoxRV.isChecked = filter.filtersState[position]
      itemView.checkBoxRV.setOnClickListener {
        filter.newFilterState(position, !filter.filtersState[position])
        filter.checkAllFiltersOneState()
        if (filter.filtersName[position]=="All"){
          if (filter.filtersState[position]){
            filter.allFilterState(true)
          }
        }
        if (filter.filtersState[0] and !filter.filtersState[position]){
          filter.filtersState[0] = false
        }
        Log.d("Movies", "${filter.filtersState[position]}")
      }

      binding.setVariable(BR.filterName, filter.filtersName[position])
      binding.executePendingBindings()
    }
  }




}