package com.practicum.appplaylistmaker.domain.models

import com.google.gson.annotations.SerializedName
import com.practicum.appplaylistmaker.MillisecondsToHumanReadable

data class Track(
    val trackId: Int,
    val trackName: String,
    val artistName: String,
    @SerializedName("trackTimeMillis")
    val trackTime: Int,
    val artworkUrl100: String,
    val releaseDate: String,
    val collectionName: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl:String
){
    fun getArtWorkUrl512() = artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")
    fun getHumanReadableDuration() = MillisecondsToHumanReadable(trackTime)
}

