package com.ims.moviesapp_kotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ims.moviesapp_kotlin.data.model.GoToMovie
import com.ims.moviesapp_kotlin.data.model.InfiniteContentScrollListener
import com.ims.moviesapp_kotlin.data.model.entity.Movie
import com.ims.moviesapp_kotlin.databinding.ListItemMovieBinding
import com.ims.moviesapp_kotlin.databinding.ListItemMovieGridBinding

class MovieListAdapter internal constructor(
    private val goToMovie : GoToMovie,
    private val infiniteContentScrollListener: InfiniteContentScrollListener
) : ListAdapter<(Movie), MovieListAdapter.ViewHolder>(MovieDiffCallback()) {
    private var isMovieItemGrid: Boolean = false

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class ViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(GoToMovie: GoToMovie, item : Movie){
                when (binding){
                    is ListItemMovieBinding -> {
                        binding.goToInterface = GoToMovie
                        binding.movie = item
                        binding.executePendingBindings()
                    }
                    is ListItemMovieGridBinding -> {
                        binding.goToInterface = GoToMovie
                        binding.movie = item
                        binding.executePendingBindings()
                    }
                    else -> throw Exception("Invalid list binding")
                }
            }

    }

    override fun submitList(list: List<Movie>?) {
        val newList: MutableList<Movie> = arrayListOf()
        if (list != null) newList.addAll(list)
        super.submitList(list)
        infiniteContentScrollListener.itemsLoaded()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (recyclerView.layoutManager is GridLayoutManager) isMovieItemGrid = true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding  : ViewDataBinding = if (isMovieItemGrid){
            ListItemMovieGridBinding.inflate(layoutInflater, parent, false)
        }else{
            ListItemMovieBinding.inflate(layoutInflater, parent, false)
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(goToMovie, getItem(position))
    }
}