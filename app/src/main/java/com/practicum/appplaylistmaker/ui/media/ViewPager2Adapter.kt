package com.practicum.appplaylistmaker.ui.media

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    :FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NFragment.newInstance("Ваша медиатека пуста", buttonVisibility = false)
            else -> NFragment.newInstance("Вы не создали\nни одного плейлиста", buttonVisibility = true)
        }
    }
}
