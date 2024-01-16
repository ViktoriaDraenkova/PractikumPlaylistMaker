package com.practicum.appplaylistmaker;

public class Track {
    var trackName: String =""
    var artistName: String=""
    var trackTime: String=""
    var artworkUrl100: String =""
    constructor(trackName: String, artistName: String, trackTime: String, artworkUrl100: String){
        this.trackName=trackName
        this.trackTime=trackTime
        this.artistName=artistName
        this.artworkUrl100=artworkUrl100
    }

}
