package com.android.example.mymusicplaylist.ui.song_selection

import android.widget.Space
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.example.mymusicplaylist.util.UiEvent

@Composable
fun SongSelectionScreen(
    onNavigate: (UiEvent) -> Unit,
    viewModel: SongSelectionViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.PopBackstack -> {
                    onNavigate(event)
                }

                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(SongSelectionEvent.OnDoneClick)
            }) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = "Selection Done")
                    if (state.value.selectedTracks.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add ${state.value.selectedTracks.size}")
                    }
                }
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.value.searchQuery,
                    onValueChange = { viewModel.onEvent(SongSelectionEvent.OnSearchQueryChange(it)) },
                    placeholder = { Text("Search by artist name...") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { viewModel.onEvent(SongSelectionEvent.OnSearchClick) }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }

            state.value.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            if (state.value.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.value.searchResults, key = { it.id }) { track ->
                        SelectionTrackItem(
                            track = track,
                            isSelected = state.value.selectedTracks.contains(track),
                            onSelectionChange = { isSelected ->
                                viewModel.onEvent(
                                    SongSelectionEvent.OnTrackSelectionChange(
                                        track,
                                        isSelected
                                    )
                                )

                            }

                        )
                    }
                }
            }
        }
    }
}