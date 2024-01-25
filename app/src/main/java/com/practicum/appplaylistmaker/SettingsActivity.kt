package com.practicum.appplaylistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val sharedPrefs = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)

        val buttonBack = findViewById<Toolbar>(R.id.back)
        buttonBack.setNavigationOnClickListener {
            finish()
        }

        val themeSwitcher =findViewById<SwitchMaterial>(R.id.themeSwitcher)

        val isDarkTheme = sharedPrefs.getBoolean(THEME_SWITCHER_KEY, false)
        themeSwitcher.isChecked = isDarkTheme
        (applicationContext as App).switchTheme(isDarkTheme)
        println(isDarkTheme)

        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            (applicationContext as App).switchTheme(checked)
            println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
            sharedPrefs.edit()
                .putBoolean(THEME_SWITCHER_KEY, checked)
                .apply()

        }

        val shareButton = findViewById<Button>(R.id.button_share)
        shareButton.setOnClickListener {
            val shareMessage = getString(R.string.link_to_the_course)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, getString(R.string.course_name)))
        }

        val supportButton = findViewById<Button>(R.id.button_to_support)
        supportButton.setOnClickListener {
            val recipientEmail = getString(R.string.my_email)
            val subject = getString(R.string.list_to_devs_pm)
            val body = getString(R.string.list_to_devs)
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", recipientEmail, null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, body)
            startActivity(Intent.createChooser(emailIntent, getString(R.string.tosupp)))
        }
        val agreementButton: Button = findViewById(R.id.button_user_agreement)
        agreementButton.setOnClickListener {
            val url = getString(R.string.offer)

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            startActivity(intent)
        }
    }
}