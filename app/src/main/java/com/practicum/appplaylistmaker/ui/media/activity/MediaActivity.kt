package com.practicum.appplaylistmaker.ui.media.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.databinding.ActivityMediaBinding
import com.practicum.appplaylistmaker.ui.media.ViewPager2Adapter

class MediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaBinding
    private lateinit var tabMediator:TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager2.adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when(position) {
                0 -> tab.text = "Избранные треки"
                else -> tab.text = "Плейлисты"
            }
        }
        tabMediator.attach()

        binding.back.setNavigationOnClickListener {
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }


}