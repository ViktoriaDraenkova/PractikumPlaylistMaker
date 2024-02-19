package com.practicum.appplaylistmaker.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.practicum.appplaylistmaker.KEY_FOR_TRACK
import com.practicum.appplaylistmaker.MillisecondsToHumanReadable
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.data.TrackRepositoryImpl
import com.practicum.appplaylistmaker.domain.api.TrackInteractor
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.domain.api.TrackRepository
import com.practicum.appplaylistmaker.domain.impl.TrackInteractorImpl
import com.practicum.appplaylistmaker.dpToPx

class AudioplayerActivity : AppCompatActivity() {

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }

    private lateinit var musicTimer: TextView
    private var playerState = STATE_DEFAULT
    private lateinit var buttonPlayStop: ImageButton
    private var mediaPlayer = MediaPlayer()
    private lateinit var track: Track
    private lateinit var handler: Handler

    private val trackRepository: TrackRepository by lazy { TrackRepositoryImpl() }
    private val trackInteractor: TrackInteractor by lazy { TrackInteractorImpl(trackRepository) }

    private val updateTimeRunnable = Runnable {
        updateTime()
    }

    private fun updateTime() {
        if (playerState != STATE_PLAYING) {
            return
        }
        musicTimer.text = MillisecondsToHumanReadable(mediaPlayer.currentPosition)
        handler.postDelayed(updateTimeRunnable, 500)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audioplayer_activity)
        initTrack()

        musicTimer = findViewById<TextView>(R.id.time_music)

        val buttonBack = findViewById<Toolbar>(R.id.back_to_list)
        buttonBack.setNavigationOnClickListener {
            finish()
        }

        buttonPlayStop = findViewById(R.id.button_play_stop)
        buttonPlayStop.setOnClickListener {
            playbackControl()
        }
        preparePlayer()

        handler = Handler(Looper.getMainLooper())
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateTimeRunnable)
        mediaPlayer.release()
    }

    private fun initTrack() {
        val arguments = intent.extras
        val trackJson: String? = arguments?.getString(KEY_FOR_TRACK)
        track = trackInteractor.getCurrentTrack(trackJson) ?: return

        val requestOptions =
            RequestOptions().transform(RoundedCorners(8.dpToPx(applicationContext))) // Скругление углов радиусом 2dp

        val trackArtwork = findViewById<ImageView>(R.id.music_icon)
        Glide.with(applicationContext).load(track.getArtWorkUrl512()).error(R.drawable.placeholder)
            .apply(requestOptions).into(trackArtwork)

        findViewById<TextView>(R.id.music_name).text = track.trackName
        findViewById<TextView>(R.id.music_author).text = track.artistName
        findViewById<TextView>(R.id.country_value).text = track.country
        findViewById<TextView>(R.id.genre_value).text = track.primaryGenreName
        findViewById<TextView>(R.id.year_value).text = track.releaseDate.take(4)
        findViewById<TextView>(R.id.album_name_value).text = track.collectionName
        findViewById<TextView>(R.id.music_duration_value).text = track.getHumanReadableDuration()
    }

    private fun preparePlayer() {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            buttonPlayStop.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            buttonPlayStop.setBackgroundResource(R.drawable.button_play);
            musicTimer.text = MillisecondsToHumanReadable(0)
            handler.removeCallbacks(updateTimeRunnable)
            playerState = STATE_PREPARED
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playerState = STATE_PLAYING
        handler.post(updateTimeRunnable)
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        handler.removeCallbacks(updateTimeRunnable)
        playerState = STATE_PAUSED
    }

    private fun playbackControl() {
        when(playerState) {
            STATE_PLAYING -> {
                buttonPlayStop.setBackgroundResource(R.drawable.button_play);
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                buttonPlayStop.setBackgroundResource(R.drawable.button_stop);
                startPlayer()
            }
        }
    }

}