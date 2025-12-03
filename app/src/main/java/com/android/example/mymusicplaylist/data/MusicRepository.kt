package com.android.example.mymusicplaylist.data

import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    suspend fun insertSong(song: Song)

    suspend fun deleteSong(song: Song)

    fun getSongs(playlistId: Int): Flow<List<Song>>

    suspend fun insertPlaylist(playlist: Playlist)

    suspend fun deletePlaylist(playlist: Playlist)

    suspend fun getPlaylistById(id: Int): Playlist

    fun getPlaylists(): Flow<List<Playlist>>

}