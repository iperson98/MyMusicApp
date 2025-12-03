package com.android.example.mymusicplaylist.ui.song_selection

import com.android.example.mymusicplaylist.data.remote.audio_db.ApiTrack

data class SongSelectionState(
    val searchQuery: String = "",
    val selectedTracks: Set<ApiTrack> = emptySet(),
    val isLoading: Boolean = false,
    val error: String? = null
)
