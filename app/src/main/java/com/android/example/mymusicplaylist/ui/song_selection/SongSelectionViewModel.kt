package com.android.example.mymusicplaylist.ui.song_selection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.example.mymusicplaylist.data.MusicRepository
import com.android.example.mymusicplaylist.data.paging.SongSelectionPagingSource
import com.android.example.mymusicplaylist.data.remote.audio_db.ApiTrack
import com.android.example.mymusicplaylist.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongSelectionViewModel @Inject constructor(
    private val repository: MusicRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val playlistId = savedStateHandle.get<Int>("playlistId")!!

    private val _state = MutableStateFlow(SongSelectionState())
    val state: StateFlow<SongSelectionState> = _state.asStateFlow()

    private val _pagingData = MutableStateFlow<Flow<PagingData<ApiTrack>>>(emptyFlow())
    val pagingData: StateFlow<Flow<PagingData<ApiTrack>>> = _pagingData.asStateFlow()

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SongSelectionEvent) {
        when (event) {
            SongSelectionEvent.OnDoneClick -> addSelectedTracksToPlaylist()
            SongSelectionEvent.OnSearchClick -> search()
            is SongSelectionEvent.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = event.query) }
            }

            is SongSelectionEvent.OnTrackSelectionChange -> {
                val currentSelected = _state.value.selectedTracks.toMutableSet()
                if (event.isSelected) {
                    currentSelected.add(event.track)
                } else {
                    currentSelected.remove(event.track)
                }
                _state.update { it.copy(selectedTracks = currentSelected) }
            }
        }
    }

    private fun search() {
        val query = _state.value.searchQuery.trim()
        if (query.isBlank()) {
            _state.update { it.copy(error = "Please enter an artist name") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            repository.searchTracksByArtist(query)
                .onSuccess { results ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = if (results.isEmpty()) "No results for \"$query\"" else null
                        )
                    }
                    // Populate page
                    _pagingData.value = Pager(
                        config = PagingConfig(
                            pageSize = 10,
                            enablePlaceholders = false
                        ),
                        pagingSourceFactory = { SongSelectionPagingSource(results) }
                    ).flow.cachedIn(viewModelScope)
                }
                .onFailure { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Search failed ${exception.message}"
                        )
                    }
                }
        }
    }

    private fun addSelectedTracksToPlaylist() {
        val selectedTracks = _state.value.selectedTracks
        if (selectedTracks.isEmpty()) {
            sendUiEvent(UiEvent.PopBackstack)
            return
        }

        viewModelScope.launch {
            selectedTracks.forEach {
                repository.addApiTrackToPlaylist(it, playlistId)
            }
        }
        sendUiEvent(
            UiEvent.ShowSnackBar(
                message = "Added ${selectedTracks.size} song(s) to playlist"
            )
        )
        sendUiEvent(UiEvent.PopBackstack)
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}