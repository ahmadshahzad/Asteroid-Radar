package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidItemBinding
import com.udacity.asteroidradar.main.AsteroidsAdapter.AsteroidViewHolder

class AsteroidsAdapter(private val clickListener: AsteroidClickListener) :
    ListAdapter<Asteroid, AsteroidViewHolder>(AsteroidDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    class AsteroidViewHolder private constructor(private val binding: AsteroidItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: AsteroidClickListener, item: Asteroid) {
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidItemBinding.inflate(layoutInflater, parent, false)

                return AsteroidViewHolder(binding)
            }
        }
    }
}

class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}

class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid) = oldItem == newItem
}