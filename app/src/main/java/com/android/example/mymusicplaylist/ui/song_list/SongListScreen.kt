package com.android.example.mymusicplaylist.ui.song_list

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.example.mymusicplaylist.util.Routes
import com.android.example.mymusicplaylist.util.UiEvent

@Composable
fun SongListScreen(
    onNavigate: (UiEvent) -> Unit,
    viewModel: SongListViewModel = hiltViewModel()
) {
    val songItems = viewModel.songItems.collectAsStateWithLifecycle(emptyList())
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
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(SongEvent.OnUndoDeleteClick)
                    }
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            Row {
                FloatingActionButton(
                    onClick = {
                        onNavigate(UiEvent.PopBackstack)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = "Go to Playlists")
                }
                Spacer(modifier = Modifier.width(8.dp))
                FloatingActionButton(
                    onClick = {
                        onNavigate(UiEvent.Navigate(Routes.SONG_SELECTION_SCREEN + "?playlistId=${viewModel.playlistId}"))
                    }
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Add Songs")
                }
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = viewModel.playlistName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(songItems.value) { song ->
                    SongItem(song, viewModel::onEvent)
                }
            }
        }
    }
}
