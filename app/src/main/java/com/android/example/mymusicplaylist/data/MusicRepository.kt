package com.android.example.mymusicplaylist.data

import com.android.example.mymusicplaylist.data.remote.ApiTrack
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    suspend fun insertSong(song: Song)

    suspend fun deleteSong(song: Song)

    fun getSongs(playlistId: Int): Flow<List<Song>>

    suspend fun insertPlaylist(playlist: Playlist)

    suspend fun deletePlaylist(playlist: Playlist)

    suspend fun getPlaylistById(id: Int): Playlist

    fun getPlaylists(): Flow<List<Playlist>>

    suspend fun searchTracksByArtist(artistName: String): Result<List<ApiTrack>>

    suspend fun addApiTrackToPlaylist(apiTrack: ApiTrack, playlistId: Int)

}