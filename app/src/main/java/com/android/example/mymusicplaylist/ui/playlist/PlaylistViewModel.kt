package com.android.example.mymusicplaylist.ui.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.mymusicplaylist.data.MusicRepository
import com.android.example.mymusicplaylist.data.Playlist
import com.android.example.mymusicplaylist.util.Routes
import com.android.example.mymusicplaylist.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val repository: MusicRepository,
) : ViewModel() {

    val playlistItems = repository.getPlaylists()

    var deletedPlaylist: Playlist? = null

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: PlaylistEvent) {
        when (event) {
            is PlaylistEvent.OnDeleteClick -> {
                viewModelScope.launch {
                    deletedPlaylist = event.playlist
                    repository.deletePlaylist(event.playlist)
                }
                sendUiEvent(
                    UiEvent.ShowSnackBar(
                        message = "Deleted Playlist",
                        action = "Undo"
                    )
                )
            }

            is PlaylistEvent.OnPlaylistClick -> {
                sendUiEvent(
                    UiEvent.Navigate(
                        route = Routes.SONG_LIST_SCREEN + "?playlistId=${event.playlistId}&playlistName=${event.playlistName}"
                    )
                )
            }

            PlaylistEvent.OnUndoDeleteClick -> {
                deletedPlaylist?.let {
                    viewModelScope.launch {
                        repository.insertPlaylist(it)
                    }
                }
            }
        }
    }

    fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}