package com.practicum.appplaylistmaker.data.audioplayer.impl

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import com.practicum.appplaylistmaker.MillisecondsToHumanReadable
import com.practicum.appplaylistmaker.data.audioplayer.Audioplayer
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.ui.audioplayer.view_model.AudioplayerViewModel

class AudioplayerImpl : Audioplayer {
    private lateinit var track: Track
    private lateinit var mediaPlayer: MediaPlayer
    override fun playMusic() {
        mediaPlayer.start()
    }

    override fun stopmusic() {
        mediaPlayer.pause()
    }

    override fun prepareTrack(
        track: Track,
        onPreparedListener: () -> Unit,
        onCompletionListener: () -> Unit
    ) {
        this.track = track
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            onPreparedListener()
        }
        mediaPlayer.setOnCompletionListener {
            onCompletionListener()
        }
    }

    override fun getTime(): String {
        return MillisecondsToHumanReadable(mediaPlayer.currentPosition)
    }
}