package com.practicum.appplaylistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<Toolbar>(R.id.back)
        buttonBack.setNavigationOnClickListener {
            finish()
        }

        val shareButton = findViewById<Button>(R.id.button_share)
        shareButton.setOnClickListener {
            val shareMessage = "https://practicum.yandex.ru/android-developer/"
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Android developer курс"))
        }

        val supportButton = findViewById<Button>(R.id.button_to_support)
        supportButton.setOnClickListener {
            val recipientEmail = "vikadraenkova@yandex.by"
            val subject = "Сообщение разработчикам и разработчицам приложения Playlist Maker"
            val body = "Спасибо разработчикам и разработчицам за крутое приложение!"
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", recipientEmail, null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, body)
            startActivity(Intent.createChooser(emailIntent, "Написать в поддержку"))
        }
        val agreementButton: Button = findViewById(R.id.button_user_agreement)
        agreementButton.setOnClickListener {
            val url = "https://yandex.ru/legal/practicum_offer/"

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            startActivity(intent)
        }
    }
}