package com.practicum.appplaylistmaker.ui.media.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.appplaylistmaker.domain.db.FavouritesInteractor
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.ui.media.FavouritesTrackState
import kotlinx.coroutines.launch

class FavouriteTracksViewModel(
    private val favouritesInteractor: FavouritesInteractor,
) : ViewModel() {
    private val stateLiveData = MutableLiveData<FavouritesTrackState>()

    fun observeState(): LiveData<FavouritesTrackState> = stateLiveData

    fun fillData(){
        viewModelScope.launch {
            favouritesInteractor.favouriteTracks().collect{tracks->
                processResult(tracks)
            }
        }
    }

    private fun processResult(tracks: List<Track>){
        if(tracks.isEmpty()){
            renderState(FavouritesTrackState.Empty)
        }
        else{renderState(FavouritesTrackState.Content(tracks))}
    }

    private fun renderState(state: FavouritesTrackState) {
        stateLiveData.postValue(state)
    }
}