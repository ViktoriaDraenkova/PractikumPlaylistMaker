package com.practicum.appplaylistmaker.ui.search.activity

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.practicum.appplaylistmaker.MillisecondsToHumanReadable
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.dpToPx


class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val soundAuthor: TextView
    private val soundName: TextView
    private val soundDuration: TextView
    private val soundImage: ImageView

    init {
        soundAuthor = itemView.findViewById(R.id.soundAuthor)
        soundName = itemView.findViewById(R.id.soundName)
        soundDuration = itemView.findViewById(R.id.soundDuration)
        soundImage = itemView.findViewById(R.id.soundImage)
    }

    fun bind(model: Track) {
        val requestOptions =
            RequestOptions().transform(RoundedCorners(2.dpToPx(itemView.context))) // Скругление углов радиусом 2dp

        soundAuthor.text = model.artistName
        soundName.text = model.trackName
        soundDuration.text = MillisecondsToHumanReadable(model.trackTime)

        Glide.with(itemView).load(model.artworkUrl100).error(R.drawable.placeholder)
            .apply(requestOptions).into(soundImage)
    }

}