package com.android.example.mymusicplaylist.ui.song_selection

import com.android.example.mymusicplaylist.data.remote.audio_db.ApiTrack

sealed class SongSelectionEvent {
    data class OnSearchQueryChange(val query: String) : SongSelectionEvent()
    data object OnSearchClick : SongSelectionEvent()
    data class OnTrackSelectionChange(val track: ApiTrack, val isSelected: Boolean) :
        SongSelectionEvent()
    data object OnDoneClick : SongSelectionEvent()
}