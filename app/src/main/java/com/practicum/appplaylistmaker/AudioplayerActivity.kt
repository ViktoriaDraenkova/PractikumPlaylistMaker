package com.practicum.appplaylistmaker

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.practicum.appplaylistmaker.api.Track

class AudioplayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "Redraw")
        setContentView(R.layout.audioplayer_activity)

        val buttonBack = findViewById<Toolbar>(R.id.back_to_list)
        buttonBack.setNavigationOnClickListener {
            finish()
        }
        val arguments = intent.extras
        val trackJson: String? = arguments?.getString(KEY_FOR_TRACK)
        val track: Track = Gson().fromJson(trackJson, Track::class.java)

        Log.d("Lalala", track.toString())

        val requestOptions =
            RequestOptions().transform(RoundedCorners(8.dpToPx(applicationContext))) // Скругление углов радиусом 2dp

        val trackArtwork = findViewById<ImageView>(R.id.music_icon)
        Glide.with(applicationContext).load(track.getArtWorkUrl512()).error(R.drawable.placeholder)
            .apply(requestOptions).into(trackArtwork)

        findViewById<TextView>(R.id.music_name).text = track.trackName
        findViewById<TextView>(R.id.time_music).text = track.getHumanReadableDuration()

        findViewById<TextView>(R.id.music_author).text=track.artistName

        findViewById<TextView>(R.id.country_value).text = track.country

        findViewById<TextView>(R.id.genre_value).text = track.primaryGenreName

        findViewById<TextView>(R.id.year_value).text = track.releaseDate.take(4)

        findViewById<TextView>(R.id.album_name_value).text = track.collectionName

        findViewById<TextView>(R.id.music_duration_value).text = track.getHumanReadableDuration()


    }
}