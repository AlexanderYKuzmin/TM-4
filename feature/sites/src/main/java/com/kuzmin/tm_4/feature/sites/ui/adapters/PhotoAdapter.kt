package com.kuzmin.tm_4.feature.sites.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuzmin.tm_4.feature.sites.databinding.ItemSitePhotoBinding
import com.kuzmin.tm_4.feature.sites.databinding.ItemSiteSimpleBinding
import com.kuzmin.tm_4.feature.sites.domain.model.samples.SiteSample
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Photo
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Site
import com.squareup.picasso.Picasso

class PhotoAdapter : ListAdapter<Photo, PhotoAdapter.ItemPhotoViewHolder>(PhotoDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoAdapter.ItemPhotoViewHolder {
        val binding = ItemSitePhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemPhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoAdapter.ItemPhotoViewHolder, position: Int) {
        val photoItem = getItem(position)
        with(holder.binding) {
            Picasso.get().load(photoItem.url).into(ivSitePhoto)
        }
    }

    inner class ItemPhotoViewHolder(
        val binding: ItemSitePhotoBinding
    ) : RecyclerView.ViewHolder(binding.root)

    object PhotoDiffCallback : DiffUtil.ItemCallback<Photo>(){
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.uuid == newItem.uuid
        }
        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }
}