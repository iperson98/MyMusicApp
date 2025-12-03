package com.android.example.mymusicplaylist.ui.song_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.mymusicplaylist.data.MusicRepository
import com.android.example.mymusicplaylist.data.Song
import com.android.example.mymusicplaylist.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongListViewModel @Inject constructor(
    private val repository: MusicRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var playlistId: Int = savedStateHandle.get<Int>("playlistId")!!
    var playlistName: String = savedStateHandle.get<String>("playlistName")!!
    val songItems =  repository.getSongs(playlistId)

    var deletedSong: Song? = null

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SongEvent) {
        when (event) {
            is SongEvent.OnDeleteClick -> {
                viewModelScope.launch {
                    deletedSong = event.song
                    repository.deleteSong(event.song)
                }
                sendUiEvent(
                    UiEvent.ShowSnackBar(
                        message = "Deleted Song",
                        action = "Undo"
                    )
                )
            }

            SongEvent.OnUndoDeleteClick -> {
                deletedSong?.let {
                    viewModelScope.launch {
                        repository.insertSong(it)
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