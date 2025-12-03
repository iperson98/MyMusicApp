package com.android.example.mymusicplaylist.ui.add_playlist

sealed class AddPlaylistEvent {
    data class OnPlaylistNameChange(val name: String): AddPlaylistEvent()
    data object OnSavePlaylistClick: AddPlaylistEvent()
}