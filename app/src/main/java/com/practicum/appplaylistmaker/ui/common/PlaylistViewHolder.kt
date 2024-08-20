package com.practicum.appplaylistmaker.ui.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.domain.models.Playlist

class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val playlistName: TextView
    private val soundImage: ImageView
    private val tracksCountInPlaylist: TextView

    init {
        playlistName = itemView.findViewById(R.id.playlist_name)
        soundImage = itemView.findViewById(R.id.playlistImageBig)
        tracksCountInPlaylist = itemView.findViewById(R.id.tracks_count_in_playlist)
    }

    fun bind(model: Playlist) {
        playlistName.text = model.playlistName
        tracksCountInPlaylist.text = "${model.tracksCountInPlaylist} треков"
        if (model.imagePath.isNotEmpty()) {
            Glide.with(itemView).load(model.imagePath).error(R.drawable.placeholder)
                .into(soundImage)
        } else {
            soundImage.setImageResource(R.drawable.placeholder)
        }
    }
}