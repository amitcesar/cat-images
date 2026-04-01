package com.github.amitcesar.cat_images.ui

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.amitcesar.cat_images.databinding.ItemCatBinding
import com.github.amitcesar.cat_images.model.CatImage

class CatAdapter : ListAdapter<CatImage, CatAdapter.CatViewHolder>(CatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding = ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CatViewHolder(private val binding: ItemCatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: CatImage) {
            binding.ivCat.load(cat.url) {
                crossfade(true)
                placeholder(R.drawable.progress_indeterminate_horizontal)
                error(R.drawable.stat_notify_error)
            }
        }
    }

    class CatDiffCallback : DiffUtil.ItemCallback<CatImage>() {
        override fun areItemsTheSame(oldItem: CatImage, newItem: CatImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatImage, newItem: CatImage): Boolean {
            return oldItem == newItem
        }
    }
}