package com.practicum.appplaylistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<Button>(R.id.back)
        buttonBack.setOnClickListener {
            val displayIntent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(displayIntent)
        }

    }


}