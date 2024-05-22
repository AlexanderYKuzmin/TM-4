package com.kuzmin.tm_4.feature.measurements.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuzmin.tm_4.common.R.*
import com.kuzmin.tm_4.feature.api.domain.model.site.McLevelInfo
import com.kuzmin.tm_4.feature.measurements.R
import com.kuzmin.tm_4.feature.measurements.databinding.ItemChildLevelDataBinding

class LevelsChildAdapter(
    val appContext: Context
)
    : ListAdapter<McLevelInfo, LevelsChildAdapter.ItemChildLevelDataViewHolder>(McLevelInfoDiffCallback){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemChildLevelDataViewHolder {
        val binding = ItemChildLevelDataBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemChildLevelDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemChildLevelDataViewHolder, position: Int) {
        val child = getItem(position)
        with(holder.binding) {
            tvLevelNum.text = child.levelNum.toString()
            tvLevelMm.text= child.shift.toString()
            tvLevelMm.setTextColor(
                if (child.isServiceable) AppCompatResources.getColorStateList(appContext, color.color_well_done)
                else AppCompatResources.getColorStateList(appContext, color.color_danger)
            )
            ivLevelState.setImageDrawable(
                if (child.isServiceable) AppCompatResources.getDrawable(appContext, R.drawable.check)
                else AppCompatResources.getDrawable(appContext, R.drawable.forbidden)
            )
        }
    }

    inner class ItemChildLevelDataViewHolder(
        val binding: ItemChildLevelDataBinding
    ) : RecyclerView.ViewHolder(binding.root)

    object McLevelInfoDiffCallback : DiffUtil.ItemCallback<McLevelInfo>() {
        override fun areItemsTheSame(oldItem: McLevelInfo, newItem: McLevelInfo): Boolean {
            return oldItem.levelNum == newItem.levelNum
        }

        override fun areContentsTheSame(oldItem: McLevelInfo, newItem: McLevelInfo): Boolean {
            return oldItem == newItem
        }
    }
}