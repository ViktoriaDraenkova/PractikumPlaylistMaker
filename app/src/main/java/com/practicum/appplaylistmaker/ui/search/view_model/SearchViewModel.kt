package com.practicum.appplaylistmaker.ui.search.view_model;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.practicum.appplaylistmaker.App
import com.practicum.appplaylistmaker.creator.Creator
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.domain.search.SearchInteractor

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    enum class ScreenState(val value: String) {
        LOADED("loaded"),
        DEFAULT("default"),
        LOADING("loading"),
        HISTORY("history"),
        NO_RESULTS("no results"),
        NO_INTERNET("no internet")
    }

    fun clearTrackHistory() {
        searchInteractor.clearTrackHistory()
        historyTracksArrayLiveData.value = emptyList()
        screenStateLiveData.value = ScreenState.DEFAULT
    }

    fun getTracksHistory(): List<Track> = searchInteractor.getTracksHistory()
    fun addTrackToHistory(track: Track) {
        searchInteractor.addTrackToHistory(track)
        historyTracksArrayLiveData.value = getTracksHistory()
    }

    private var historyTracksArrayLiveData = MutableLiveData<List<Track>>()
    private var tracksLiveData = MutableLiveData<List<Track>>()
    private var screenStateLiveData = MutableLiveData(ScreenState.DEFAULT)

    fun search(trackName: String) {
        tracksLiveData.value = emptyList()
        screenStateLiveData.value = ScreenState.LOADING
        searchInteractor.searchTrack(
            trackName,
            { tracks ->
                tracksLiveData.value = tracks
                if (tracksLiveData.value?.isEmpty() == true) {
                    screenStateLiveData.value = ScreenState.NO_RESULTS
                } else {
                    screenStateLiveData.value = ScreenState.LOADED
                }
            },
            {
                screenStateLiveData.value = ScreenState.NO_INTERNET
            }
        )
    }

    fun showHistory() {
        historyTracksArrayLiveData.value = getTracksHistory()
        if (!historyTracksArrayLiveData.value!!.isEmpty()) {
            screenStateLiveData.value = ScreenState.HISTORY
        }
        tracksLiveData.value = emptyList()
    }

    fun toDefault() {
        screenStateLiveData.value = ScreenState.DEFAULT
        tracksLiveData.value = emptyList()
    }


    fun getScreenStateLiveData(): LiveData<ScreenState> {
        return screenStateLiveData
    }



    fun getTracksLiveData(): LiveData<List<Track>> {
        return tracksLiveData
    }

    fun getTracksHistoryLiveData(): LiveData<List<Track>> {
        return historyTracksArrayLiveData
    }


}
