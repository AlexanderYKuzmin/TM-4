package com.kuzmin.tm_4.feature.sites.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuzmin.tm_4.common.extension.formatToDateString
import com.kuzmin.tm_4.feature.sites.R
import com.kuzmin.tm_4.feature.sites.databinding.ItemSiteSimpleBinding
import com.kuzmin.tm_4.feature.sites.domain.model.samples.SiteSample
import com.squareup.picasso.Picasso

class SitesAdapter(
    private val appContext: Context
)
    : ListAdapter<SiteSample, SitesAdapter.ItemSiteSampleViewHolder>(SiteSimpleDiffCallback) {

        var onItemClickListener: ((Long) -> Unit)? = null
        //var onItemLongClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemSiteSampleViewHolder {
        val binding = ItemSiteSimpleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemSiteSampleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemSiteSampleViewHolder, position: Int) {
        val sampleSite = getItem(position)

        with(holder.binding) {
            with(sampleSite) {
                //root.background = getBackgroundDrawable(isChosen) // нет не так буду делать
                tvBuildingName.text = name
                tvAddressBuilding.text = address.toString()
                constructionsSample?.let {
                    with(it[0]) {
                        tvCreationDate.text = creationDate?.formatToDateString()
                        tvBuildingType.text = getTypeRuValue()
                        tvBuildingHeight.text = getHeight()
                        tvMeasuresDate.text = completedDate?.formatToDateString()
                            ?: appContext.getText(R.string.measures_none)
                        //ivChecked.background = getCheckedDrawable(isCompleted)
                        tvMeasuresDate.setTextColor(getTextColor(isCompleted))
                    }
                }
                Picasso.get().load(photoUrl).into(ivBuilding)
                root.setOnClickListener {
                    onItemClickListener?.invoke(remoteId)
                }
            }
        }
    }

   /* private fun getCheckedDrawable(isCompleted: Boolean): Drawable {
        return if (isCompleted) ContextCompat.getDrawable(appContext, com.kuzmin.tm_4.common.R.drawable.checkmark_svgrepo_com)!!
        else ContextCompat.getDrawable(appContext, com.kuzmin.tm_4.common.R.drawable.unchecked)!!
    }*/

    /*private fun getBackgroundDrawable(isChosen: Boolean): Drawable {
        return if (isChosen) ContextCompat.getDrawable(appContext, com.kuzmin.tm_4.common.R.drawable.item_site_background_ch)!!
        else ContextCompat.getDrawable(appContext, com.kuzmin.tm_4.common.R.drawable.item_site_background)!!
    }*/

    private fun getTextColor(isCompleted: Boolean): Int {
        return if (isCompleted) appContext.getColor(com.kuzmin.tm_4.common.R.color.color_well_done)
        else appContext.getColor(com.kuzmin.tm_4.common.R.color.color_danger)
    }

    inner class ItemSiteSampleViewHolder(
        val binding: ItemSiteSimpleBinding
    ) : RecyclerView.ViewHolder(binding.root)


    object SiteSimpleDiffCallback : DiffUtil.ItemCallback<SiteSample>(){
        override fun areItemsTheSame(oldItem: SiteSample, newItem: SiteSample): Boolean {
            return oldItem.remoteId == newItem.remoteId
        }
        override fun areContentsTheSame(oldItem: SiteSample, newItem: SiteSample): Boolean {
            return oldItem == newItem
        }
    }


}