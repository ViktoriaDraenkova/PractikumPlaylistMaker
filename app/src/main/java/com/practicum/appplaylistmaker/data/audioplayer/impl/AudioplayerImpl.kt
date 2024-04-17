package com.practicum.appplaylistmaker.data.audioplayer.impl


import android.media.MediaPlayer
import com.practicum.appplaylistmaker.MillisecondsToHumanReadable
import com.practicum.appplaylistmaker.data.audioplayer.Audioplayer
import com.practicum.appplaylistmaker.domain.models.Track


class AudioplayerImpl(private val mediaPlayer: MediaPlayer) : Audioplayer {
    private lateinit var track: Track

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