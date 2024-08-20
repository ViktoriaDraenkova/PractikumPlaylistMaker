package com.practicum.appplaylistmaker.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.ui.media.PlaylistClickListener

class LittlePlaylistAdapter(val playlistClickListener: PlaylistClickListener) : RecyclerView.Adapter<LittlePlaylistViewHolder>() {

    var playlists = emptyList<Playlist>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LittlePlaylistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.little_playlist_view_for_bottomsheet, parent, false)
        return LittlePlaylistViewHolder(view)

    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: LittlePlaylistViewHolder, position: Int) {
        holder.bind(playlists[position])
        holder.itemView.setOnClickListener{playlistClickListener.onPlaylistClick(playlists[position])}
    }
}