package com.practicum.appplaylistmaker.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.ui.search.fragment.TrackClickListener

class MusicAdapter(
    private val clickListener: TrackClickListener,
    private val longPressListener: TrackLongPressListener? = null
) : RecyclerView.Adapter<MusicViewHolder> () {

    var tracks = emptyList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.little_music_view, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener { clickListener.onTrackClick(tracks[position]) }
        holder.itemView.setOnLongClickListener { longPressListener?.onTrackLongPress(tracks[position]); true }
    }

    override fun getItemCount() = tracks.size
}