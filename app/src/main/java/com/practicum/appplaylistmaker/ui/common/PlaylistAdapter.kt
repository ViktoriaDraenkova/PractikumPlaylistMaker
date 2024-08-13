package com.practicum.appplaylistmaker.ui.common

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.domain.models.Playlist

class PlaylistAdapter() : RecyclerView.Adapter<PlaylistViewHolder>() {

    var playlists = emptyList<Playlist>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.little_playlist_view, parent, false)
        return PlaylistViewHolder(view)

    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlists[position])
//        holder.itemView.setOnClickListener{clickListener.onPlaylistClick(playlists[position])}
    }
}