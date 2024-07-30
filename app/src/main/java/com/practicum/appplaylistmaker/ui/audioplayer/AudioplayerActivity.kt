package com.practicum.appplaylistmaker.ui.audioplayer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.practicum.appplaylistmaker.KEY_FOR_TRACK
import com.practicum.appplaylistmaker.MillisecondsToHumanReadable
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.databinding.AudioplayerActivityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.dpToPx
import com.practicum.appplaylistmaker.ui.audioplayer.view_model.AudioplayerViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeActivity

class AudioplayerActivity : AppCompatActivity() {

    private lateinit var binding: AudioplayerActivityBinding
    private lateinit var musicTimer: TextView
    private lateinit var buttonPlayStop: ImageButton
    private val viewModel: AudioplayerViewModel by viewModel()
    private var timerJob: Job? = null

    private suspend fun updateTime() {
        while (viewModel.getPlayerState().value == AudioplayerViewModel.AudioplayerState.STATE_PLAYING) {
            musicTimer.text = viewModel.getTimeState()
            delay(300)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AudioplayerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTrack(getTrack())

        viewModel.getPlayerState().observe(this) { playerState ->
            when (playerState) {
                AudioplayerViewModel.AudioplayerState.STATE_PAUSED -> {
                    buttonPlayStop.setBackgroundResource(R.drawable.button_play)
                    timerJob?.cancel()
                }

                AudioplayerViewModel.AudioplayerState.STATE_PLAYING -> {
                    buttonPlayStop.setBackgroundResource(R.drawable.button_stop)
                    timerJob = lifecycleScope.launch {
                        updateTime()
                    }
                }

                AudioplayerViewModel.AudioplayerState.STATE_PREPARED -> {
                    buttonPlayStop.setBackgroundResource(R.drawable.button_play)
                    buttonPlayStop.isEnabled = true
                    musicTimer.text = MillisecondsToHumanReadable(0)
                }

                AudioplayerViewModel.AudioplayerState.STATE_DEFAULT -> {
                    buttonPlayStop.isEnabled = false
                }
            }
        }

        viewModel.getTrackLikedState().observe(this) { trackLikeState ->
            when (trackLikeState) {
                AudioplayerViewModel.LikeState.STATE_LIKED -> {
                    binding.buttonFavourite.setBackgroundResource(R.drawable.button_liked)
                }

                AudioplayerViewModel.LikeState.STATE_DISLIKED -> {
                    binding.buttonFavourite.setBackgroundResource(R.drawable.button_disliked)
                }
            }
        }

        musicTimer = findViewById(R.id.time_music)

        binding.backToList.setNavigationOnClickListener {
            finish()
        }
        binding.buttonFavourite.setOnClickListener {

            when (viewModel.getTrackLikedState().value) {
                AudioplayerViewModel.LikeState.STATE_DISLIKED -> {
                    viewModel.likeTrack(getTrack())
                }
                AudioplayerViewModel.LikeState.STATE_LIKED -> {
                    viewModel.dislikeTrack(getTrack())
                }

                else -> {}
            }
        }
        buttonPlayStop = binding.buttonPlayStop
        binding.buttonPlayStop.setOnClickListener {
            playbackControl()
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        timerJob?.cancel()
    }

    private fun getTrack(): Track {
        val arguments = intent.extras
        val trackJson: String? = arguments?.getString(KEY_FOR_TRACK)
        return Gson().fromJson(trackJson, Track::class.java)
    }

    private fun initTrack(track: Track) {
        val requestOptions =
            RequestOptions().transform(RoundedCorners(8.dpToPx(applicationContext))) // Скругление углов радиусом 2dp

        viewModel.setTrack(track)

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

    private fun startPlayer() {
        viewModel.startPlayer()
    }

    private fun pausePlayer() {
        viewModel.pausePlayer()
    }

    private fun playbackControl() {
        when (viewModel.getPlayerState().value) {
            AudioplayerViewModel.AudioplayerState.STATE_PLAYING -> {
                pausePlayer()
            }

            AudioplayerViewModel.AudioplayerState.STATE_PREPARED, AudioplayerViewModel.AudioplayerState.STATE_PAUSED -> {
                startPlayer()
            }

            else -> {}
        }
    }
}