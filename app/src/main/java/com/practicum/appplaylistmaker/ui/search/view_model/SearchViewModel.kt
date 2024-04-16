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
        historyTracksArrayLiveData.value?.clear()
        screenStateLiveData.value = ScreenState.DEFAULT
    }

    fun getTracksHistory(): ArrayList<Track> = searchInteractor.getTracksHistory()
    fun addTrackToHistory(track: Track) {
        searchInteractor.addTrackToHistory(track)
        historyTracksArrayLiveData.value = getTracksHistory()
    }

    private var historyTracksArrayLiveData = MutableLiveData<ArrayList<Track>>()
    private var tracksLiveData = MutableLiveData<ArrayList<Track>>()
    private var screenStateLiveData = MutableLiveData(ScreenState.DEFAULT)

    fun search(trackName: String) {
        tracksLiveData.value?.clear()
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
        tracksLiveData.value?.clear()
    }

    fun toDefault() {
        screenStateLiveData.value = ScreenState.DEFAULT
        tracksLiveData.value?.clear()
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val application =
                        checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App

                    return SearchViewModel(
                        Creator.getSearchInteractor(application)
                    ) as T
                }
            }
    }

    fun getScreenStateLiveData(): LiveData<ScreenState> {
        return screenStateLiveData
    }



    fun getTracksLiveData(): LiveData<ArrayList<Track>> {
        return tracksLiveData
    }

    fun getTracksHistoryLiveData(): LiveData<ArrayList<Track>> {
        return historyTracksArrayLiveData
    }


}
