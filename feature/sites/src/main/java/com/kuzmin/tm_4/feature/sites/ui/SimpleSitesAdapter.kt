package com.kuzmin.tm_4.feature.sites.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuzmin.tm_4.feature.sites.databinding.ItemSiteSimpleBinding
import com.kuzmin.tm_4.feature.sites.domain.SiteSimple

class SimpleSitesAdapter()
    : ListAdapter<SiteSimple, SimpleSitesAdapter.ItemBuildingSimpleViewHolder>(SiteSimpleDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemBuildingSimpleViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ItemBuildingSimpleViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


    inner class ItemBuildingSimpleViewHolder(
        private val binding: ItemSiteSimpleBinding
    ) : RecyclerView.ViewHolder(binding.root)


    object SiteSimpleDiffCallback : DiffUtil.ItemCallback<SiteSimple>(){
        override fun areItemsTheSame(oldItem: SiteSimple, newItem: SiteSimple): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: SiteSimple, newItem: SiteSimple): Boolean {
            return oldItem == newItem
        }
    }
}