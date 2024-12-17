package com.a1developers.whatsappstatussaver.views.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.a1developers.whatsappstatussaver.views.fragments.FragmentMedia
import com.devatrii.statussaver.utils.Constants

class MediaViewPagerAdapter(
    private val fragmentActivity: FragmentActivity,
    private val imagesType: String = Constants.MEDIA_TYPE_WHATSAPP_IMAGES,
    private val videosType: String = Constants.MEDIA_TYPE_WHATSAPP_VIDEOS
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                val mediaFragment = FragmentMedia()
                val bundle = Bundle()
                bundle.putString(Constants.MEDIA_TYPE_KEY, imagesType)
                mediaFragment.arguments = bundle
                mediaFragment
            }


            else -> {
                val mediaFragment = FragmentMedia()
                val bundle = Bundle()
                bundle.putString(Constants.MEDIA_TYPE_KEY, videosType)
                mediaFragment.arguments = bundle
                mediaFragment
            }
        }
    }
}
