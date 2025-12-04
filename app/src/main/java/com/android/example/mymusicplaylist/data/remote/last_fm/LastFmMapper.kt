package com.android.example.mymusicplaylist.data.remote.last_fm

import com.android.example.mymusicplaylist.data.Song
import com.android.example.mymusicplaylist.data.remote.audio_db.ApiTrack

fun LastFmTrack.toApiTrack(): ApiTrack {
    return ApiTrack(
        id = mbid ?: name.hashCode().toString(),
        name = name,
        artistName = artist.name,
        albumName = null,
        thumbnail = images.getLargeImage()
    )
}

fun LastFmTrack.toSongEntity(playlistId: Int): Song {
    return Song(
        trackId = mbid ?: name.hashCode().toString(),
        playlistId = playlistId,
        name = name,
        artistName = artist.name,
        albumName = null,
        thumbnailUrl = images.getLargeImage() ?: ""
    )
}


