package com.android.example.mymusicplaylist.data.remote.audio_db

import com.android.example.mymusicplaylist.data.Song

fun ApiTrack.toSongEntity(playlistId: Int): Song {
    return Song(
        trackId = id,
        playlistId = playlistId,
        name = name,
        artistName = artistName,
        albumName = albumName,
        thumbnailUrl = thumbnail ?: ""
    )
}