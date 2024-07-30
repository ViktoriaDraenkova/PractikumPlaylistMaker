package com.practicum.appplaylistmaker.ui.media.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.databinding.FragmentMediaBinding
import com.practicum.appplaylistmaker.ui.media.ViewPager2Adapter

class MediaFragment : Fragment()  {

    private lateinit var binding: FragmentMediaBinding
    private lateinit var tabMediator:TabLayoutMediator


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentMediaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewPager2.adapter = ViewPager2Adapter(childFragmentManager, lifecycle)
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when(position) {
                0 -> tab.setText(R.string.favourite_tracks)
                else -> tab.setText(R.string.playlists)
            }
        }
        tabMediator.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


}