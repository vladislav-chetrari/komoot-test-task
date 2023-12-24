package com.komoot.vchetrari.challenge.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.komoot.vchetrari.challenge.data.model.Photo
import com.komoot.vchetrari.challenge.databinding.ListItemPhotoBinding

class PhotoListAdapter : ListAdapter<Photo, PhotoListAdapter.ListItemViewHolder>(ItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListItemViewHolder(
        ListItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) = holder.bind(getItem(position))

    class ListItemViewHolder(private val binding: ListItemPhotoBinding) : ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            Glide.with(itemView)
                .load(photo.url)
                .into(binding.root)
        }
    }

    private class ItemCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem
    }
}