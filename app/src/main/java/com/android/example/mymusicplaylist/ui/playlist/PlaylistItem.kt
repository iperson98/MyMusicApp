package com.android.example.mymusicplaylist.ui.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.android.example.mymusicplaylist.data.Playlist

@Composable
fun PlaylistItem(
    playlist: Playlist,
    onEvent: (PlaylistEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = playlist.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onEvent(
                            PlaylistEvent.OnPlaylistClick(
                                playlist.id,
                                playlist.name
                            )
                        )
                    })
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Playlist",
                Modifier.clickable { onEvent(PlaylistEvent.OnDeleteClick(playlist)) })
        }
    }
}