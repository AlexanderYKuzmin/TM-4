package com.kuzmin.tm_4.feature.measurements.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuzmin.tm_4.common.extension.formatToDateString
import com.kuzmin.tm_4.feature.api.model.McLevelInfo
import com.kuzmin.tm_4.feature.measurements.R
import com.kuzmin.tm_4.feature.measurements.databinding.ItemMeasurementConstructionBinding
import com.kuzmin.tm_4.feature.measurements.ui.model.McParent

class MeasurementConstructionsAdapter(
    val appContext: Context
)
    : ListAdapter<McParent, MeasurementConstructionsAdapter.ItemMeasurementConstructionViewHolder>(McParentDiffCallback){

//set on click listener On Parent

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemMeasurementConstructionViewHolder {
        val binding = ItemMeasurementConstructionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ItemMeasurementConstructionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemMeasurementConstructionViewHolder, position: Int) {
        val binding = holder.binding
        val parent = getItem(position)

        with(binding) {
            tvDateCompleted.text = parent.date.formatToDateString()
            tvMcEmployee.text = parent.employee
            tvMcConclusion.text = parent.getConclusion(appContext)
        }

         binding.ivLevels.setOnClickListener {
             val upAnim = AnimationUtils.loadAnimation(appContext, R.anim.up)
             val downAnim = AnimationUtils.loadAnimation(appContext, R.anim.down)
            parent.isOpen = !parent.isOpen

            if (parent.isOpen && parent.levelsInfo != null) {
                binding.llMcCommon.startAnimation(downAnim)
                binding.rvChildLevels.startAnimation(downAnim)
                setChildRecyclerView(binding.rvChildLevels, parent.levelsInfo)

                binding.ivLevels.setImageDrawable(
                    AppCompatResources.getDrawable(appContext, R.drawable.arrow_down)
                )
            } else {
                binding.llMcCommon.startAnimation(upAnim)
                binding.rvChildLevels.visibility = View.GONE
                binding.ivLevels.setImageDrawable(
                    AppCompatResources.getDrawable(appContext, R.drawable.arrow_right)
                )
            }
        }
    }

    private fun setChildRecyclerView(child: RecyclerView, levels: List<McLevelInfo>) {
        child.visibility = View.VISIBLE
        val adapter = LevelsChildAdapter(appContext)
        child.adapter = adapter
        adapter.submitList(levels)
    }


    inner class ItemMeasurementConstructionViewHolder(
        val binding: ItemMeasurementConstructionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.rvChildLevels.setHasFixedSize(true)
            binding.rvChildLevels.layoutManager = LinearLayoutManager(binding.root.context)
        }
    }

    object McParentDiffCallback : DiffUtil.ItemCallback<McParent>(){
        override fun areItemsTheSame(oldItem: McParent, newItem: McParent): Boolean {
            return oldItem.uuid == newItem.uuid
        }
        override fun areContentsTheSame(oldItem: McParent, newItem: McParent): Boolean {
            return oldItem == newItem
        }
    }


}