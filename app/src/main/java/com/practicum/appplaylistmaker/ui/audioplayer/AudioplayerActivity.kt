package com.practicum.appplaylistmaker.ui.audioplayer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.practicum.appplaylistmaker.KEY_FOR_TRACK
import com.practicum.appplaylistmaker.MillisecondsToHumanReadable
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.databinding.AudioplayerActivityBinding
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.dpToPx
import com.practicum.appplaylistmaker.ui.audioplayer.view_model.AudioplayerViewModel
import com.practicum.appplaylistmaker.ui.common.LittlePlaylistAdapter
import com.practicum.appplaylistmaker.ui.media.PlaylistClickListener
import com.practicum.appplaylistmaker.ui.new_playlist.fragment.NewPlaylistFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AudioplayerActivity : AppCompatActivity() {

    private lateinit var binding: AudioplayerActivityBinding
    private lateinit var musicTimer: TextView
    private lateinit var buttonPlayStop: ImageButton
    private val viewModel: AudioplayerViewModel by viewModel()
    private var timerJob: Job? = null
    private lateinit var currentTrack: Track
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheetContainer:LinearLayout

    private val adapter =
        LittlePlaylistAdapter { playlist ->
            if (playlist.tracks.contains(currentTrack)) {
                showToast("Трек уже добавлен в плейлист ${playlist.playlistName}")
            } else {
                addToPlaylist(currentTrack, playlist.playlistId)
                showToast("Добавлено в плейлист ${playlist.playlistName} ")
                viewModel.fillPlaylistData()
                bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetContainer).apply {
                    state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun addToPlaylist(track: Track, playlistId: Long) {
        viewModel.insertTrackToPlaylist(track, playlistId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AudioplayerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentTrack = getTrack()
        initTrack(currentTrack)

        bottomSheetContainer = binding.playlistsBottomSheet

        val overlay = binding.overlay

        viewModel.getObservablePlaylists().observe(this) { playlists ->
            Log.d("playlists", playlists.toString())

            adapter.playlists = playlists
            adapter.notifyDataSetChanged()
        }

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetContainer).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {

                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        overlay.visibility = View.GONE
                    }

                    else -> {
                        overlay.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

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

        binding.addToPlaylist.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            viewModel.fillPlaylistData()
        }


        binding.layoutPersistentBottomSheet.buttonNewPlaylist.setOnClickListener {
            openNewPlaylistFragment()
        }

        val recyclerPlaylistView = binding.layoutPersistentBottomSheet.recyclerView
        recyclerPlaylistView?.layoutManager = GridLayoutManager(this, 1)
        if (recyclerPlaylistView != null) {
            recyclerPlaylistView.adapter = adapter
        }

        viewModel.fillPlaylistData()


    }

    private fun openNewPlaylistFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.audioplayer_nav_host_fragment,
            NewPlaylistFragment.newInstance(true)
        )
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        binding.scrollviewWithTrack.visibility = View.GONE
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        timerJob?.cancel()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("BackPress", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
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

    private suspend fun updateTime() {
        while (viewModel.getPlayerState().value == AudioplayerViewModel.AudioplayerState.STATE_PLAYING) {
            musicTimer.text = viewModel.getTimeState()
            delay(300)
        }
    }
}