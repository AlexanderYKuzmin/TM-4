package com.kuzmin.tm_4.feature.sites.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuzmin.tm_4.common.R.drawable.item_site_background
import com.kuzmin.tm_4.common.R.drawable.item_site_background_local
import com.kuzmin.tm_4.common.extension.formatToDateString
import com.kuzmin.tm_4.common.extension.getContextDrawable
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_LOCAL
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_REMOTE
import com.kuzmin.tm_4.feature.sites.R
import com.kuzmin.tm_4.feature.sites.databinding.ItemSiteSimpleBinding
import com.kuzmin.tm_4.feature.api.domain.model.sample.SiteSample
import com.squareup.picasso.Picasso

class SiteListAdapter(
    private val appContext: Context,
    private val location: Int
)
    : ListAdapter<SiteSample, SiteListAdapter.ItemSiteSampleViewHolder>(SiteSimpleDiffCallback) {

        var onItemClickListener: ((String, String, String) -> Unit)? = null
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
        Log.d("Get All", "PhotoUrl = ${sampleSite.photoUrl}")
        with(holder.binding) {

            clSampleSite.background = when (location) {
                STORAGE_LOCAL -> appContext.getContextDrawable(item_site_background_local)
                STORAGE_REMOTE -> appContext.getContextDrawable(item_site_background)
                else -> throw RuntimeException("Location is not defined")
            }

            with(sampleSite) {
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
                if (location == STORAGE_REMOTE) Picasso.get().load(photoUrl).into(ivBuilding)
                else {
                    Log.d("Get All", "Find photo")
                }

                root.setOnClickListener {
                    onItemClickListener?.invoke(uuid, name, constructionsSample!!.first().uuid)
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
            return oldItem.uuid == newItem.uuid
        }
        override fun areContentsTheSame(oldItem: SiteSample, newItem: SiteSample): Boolean {
            return oldItem == newItem
        }
    }
}