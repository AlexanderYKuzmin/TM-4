package com.kuzmin.tm_4.feature.sites.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kuzmin.tm_4.feature.api.domain.model.site.Photo
import com.kuzmin.tm_4.feature.sites.ui.fragments.ItemPagerFragment

class PagerPhotoAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment){

    var photos: List<Photo>? = null

    override fun getItemCount(): Int {
        return photos?.size ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        return ItemPagerFragment.newInstance(photos?.get(position)?.url)
    }
}