package com.android.example.mymusicplaylist.ui.add_playlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.mymusicplaylist.data.MusicRepository
import com.android.example.mymusicplaylist.data.Playlist
import com.android.example.mymusicplaylist.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlaylistViewModel @Inject constructor(
    private val repository: MusicRepository
) : ViewModel() {

    var playlistName = mutableStateOf("")
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AddPlaylistEvent) {
        when (event) {
            is AddPlaylistEvent.OnPlaylistNameChange -> {
                playlistName.value = event.name
            }

            AddPlaylistEvent.OnSavePlaylistClick -> {
                if (playlistName.value.isBlank()) {
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            message = "Playlist cannot have an empty name"
                        )
                    )
                } else {
                    viewModelScope.launch {
                        repository.insertPlaylist(
                            Playlist(
                                name = playlistName.value
                            )
                        )
                    }
                    sendUiEvent(UiEvent.PopBackstack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}