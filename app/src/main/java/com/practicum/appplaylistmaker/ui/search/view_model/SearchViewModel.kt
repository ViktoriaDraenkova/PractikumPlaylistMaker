package com.practicum.appplaylistmaker.ui.search.view_model;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.domain.search.SearchInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    enum class ScreenState(val value: String) {
        LOADED("loaded"),
        DEFAULT("default"),
        LOADING("loading"),
        HISTORY("history"),
        NO_RESULTS("no results"),
        NO_INTERNET("no internet")
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
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
    private var searchJob: Job? = null
    private var latestSearchText: String? = null
    fun searchDebounce(changedText: String) {

        if (latestSearchText == changedText) {
            return
        }

        latestSearchText = changedText

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            search(changedText)
        }
    }

    fun search(trackName: String) {
        tracksLiveData.value = emptyList()
        screenStateLiveData.value = ScreenState.LOADING

        viewModelScope.launch {
            searchInteractor.searchTrack(trackName).collect { tracks ->
                if (tracks == null) {
                    screenStateLiveData.value = ScreenState.NO_INTERNET
                    return@collect
                }
                // onSuccess
                tracksLiveData.value = tracks
                if (tracksLiveData.value?.isEmpty() == true) {
                    screenStateLiveData.value = ScreenState.NO_RESULTS
                } else {
                    screenStateLiveData.value = ScreenState.LOADED
                }
            }
        }
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
