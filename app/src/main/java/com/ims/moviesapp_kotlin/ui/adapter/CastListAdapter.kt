package com.ims.moviesapp_kotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ims.moviesapp_kotlin.data.model.GoToCast
import com.ims.moviesapp_kotlin.data.model.entity.Cast
import com.ims.moviesapp_kotlin.databinding.ListItemCastBinding

class CastListAdapter internal constructor(
    private val goToCast: GoToCast
) : ListAdapter<(Cast), CastListAdapter.ViewHolder>(CastDiffCallback()) {

    class ViewHolder(
        private val binding: ListItemCastBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(goToCast: GoToCast, item: Cast){
            binding.goToInterface = goToCast
            binding.cast = item
            binding.executePendingBindings()
        }
    }

    private class CastDiffCallback : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemCastBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(goToCast, getItem(position))
    }
}