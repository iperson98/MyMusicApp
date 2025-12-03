package com.android.example.mymusicplaylist.ui.playlist

import com.android.example.mymusicplaylist.data.Playlist

sealed class PlaylistEvent {
    data class OnPlaylistClick(val playlistId: Int): PlaylistEvent()
    data class OnDeleteClick(val playlist: Playlist): PlaylistEvent()
    data object OnUndoDeleteClick: PlaylistEvent()
}