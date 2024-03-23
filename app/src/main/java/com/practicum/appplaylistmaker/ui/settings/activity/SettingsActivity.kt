package com.practicum.appplaylistmaker.ui.settings.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.practicum.playlist.ui.settings.view_model.SettingsViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import com.practicum.appplaylistmaker.PRACTICUM_EXAMPLE_PREFERENCES
import com.practicum.appplaylistmaker.R

class SettingsActivity : ComponentActivity() {
    private lateinit var viewModel: SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            SettingsViewModel.getViewModelFactory()
        )[SettingsViewModel::class.java]

        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<Toolbar>(R.id.back)
        buttonBack.setNavigationOnClickListener {
            finish()
        }

        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)
        themeSwitcher.isChecked = viewModel.getTheme()
        viewModel.initTheme()

        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.setTheme(checked)
        }

        val shareButton = findViewById<Button>(R.id.button_share)
        shareButton.setOnClickListener {
            viewModel.onShareAppClicked()
        }

        val supportButton = findViewById<Button>(R.id.button_to_support)
        supportButton.setOnClickListener {
            viewModel.onSupportClicked()
        }
        val agreementButton: Button = findViewById(R.id.button_user_agreement)
        agreementButton.setOnClickListener {
            viewModel.onUserAgreementClicked()
        }
    }
}