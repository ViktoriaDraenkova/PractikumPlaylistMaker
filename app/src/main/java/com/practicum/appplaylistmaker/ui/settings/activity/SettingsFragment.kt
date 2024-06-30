package com.practicum.appplaylistmaker.ui.settings.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.practicum.playlist.ui.settings.view_model.SettingsViewModel
import com.practicum.appplaylistmaker.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private val viewModel: SettingsViewModel by viewModel()
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val themeSwitcher = binding.themeSwitcher
        themeSwitcher.isChecked = viewModel.getTheme()
        viewModel.setTheme(themeSwitcher.isChecked)

        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.setTheme(checked)
        }

        binding.buttonShare.setOnClickListener {
            viewModel.onShareAppClicked()
        }
        binding.buttonToSupport.setOnClickListener {
            viewModel.onSupportClicked()
        }
        binding.buttonUserAgreement.setOnClickListener {
            viewModel.onUserAgreementClicked()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}