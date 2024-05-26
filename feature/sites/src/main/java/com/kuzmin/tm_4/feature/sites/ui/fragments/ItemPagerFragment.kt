package com.kuzmin.tm_4.feature.sites.ui.fragments

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kuzmin.tm_4.feature.sites.R
import com.kuzmin.tm_4.feature.sites.databinding.ItemSitePhotoBinding
import com.squareup.picasso.Picasso

private const val PHOTO_URL = "photo_url"


class ItemPagerFragment : Fragment() {
    private var photoUrl: String? = null

    private lateinit var binding: ItemSitePhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photoUrl = it.getString(PHOTO_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ItemSitePhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get().load(photoUrl).into(binding.ivSitePhoto)
    }

    companion object {
        @JvmStatic
        fun newInstance(photoUrl: String?) =
            ItemPagerFragment().apply {
                arguments = Bundle().apply {
                    putString(PHOTO_URL, photoUrl)
                }
            }
    }
}