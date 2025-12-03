package com.android.example.mymusicplaylist.ui.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.example.mymusicplaylist.data.Playlist

@Composable
fun PlaylistItem(
    playlist: Playlist,
    onEvent: (PlaylistEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = playlist.name,
            modifier = Modifier
                .weight(1f)
                .clickable { onEvent(PlaylistEvent.OnPlaylistClick(playlist.id)) })
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Playlist",
            Modifier.clickable { onEvent(PlaylistEvent.OnDeleteClick(playlist)) })
    }
}