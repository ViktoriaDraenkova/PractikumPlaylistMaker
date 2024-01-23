package com.practicum.recyclerview_lesson_1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.TrackClickListener
import com.practicum.appplaylistmaker.api.SearchTrackResponse
import com.practicum.appplaylistmaker.api.Track

class MusicAdapter(
    val clickListener: TrackClickListener
) : RecyclerView.Adapter<MusicViewHolder> () {

    var tracks = ArrayList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.little_music_view, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener { clickListener.onTrackClick(tracks.get(position)) }
    }

    override fun getItemCount() = tracks.size
}