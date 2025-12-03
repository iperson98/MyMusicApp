package com.android.example.mymusicplaylist.ui.song_list

import com.android.example.mymusicplaylist.data.Song

sealed class SongEvent {
    data class OnDeleteClick(val song: Song): SongEvent()
    data object OnUndoDeleteClick: SongEvent()
}