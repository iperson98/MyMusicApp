package com.android.example.mymusicplaylist.ui.playlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.example.mymusicplaylist.util.Routes
import com.android.example.mymusicplaylist.util.UiEvent

@Composable
fun PlaylistScreen(
    onNavigate: (UiEvent) -> Unit,
    viewModel: PlaylistViewModel = hiltViewModel()
) {
    val playlistItems = viewModel.playlistItems.collectAsStateWithLifecycle(emptyList())
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(UiEvent.Navigate(event.route))
                }

                is UiEvent.ShowSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short)
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(PlaylistEvent.OnUndoDeleteClick)
                    }
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigate(UiEvent.Navigate(Routes.ADD_PLAYLIST_SCREEN))
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Playlist")
            }
        }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(playlistItems.value) { playlist ->
                PlaylistItem(playlist, viewModel::onEvent)
            }
        }
    }
}