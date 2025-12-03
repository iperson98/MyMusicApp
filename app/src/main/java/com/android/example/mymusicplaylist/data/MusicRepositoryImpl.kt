package com.android.example.mymusicplaylist.data

import kotlinx.coroutines.flow.Flow

class MusicRepositoryImpl(private val dao: MusicDao): MusicRepository {
    override suspend fun insertSong(song: Song) {
        dao.insertSong(song)
    }

    override suspend fun deleteSong(song: Song) {
        dao.deleteSong(song)
    }

    override fun getSongs(playlistId: Int): Flow<List<Song>> {
        return dao.getSongs(playlistId)
    }

    override suspend fun insertPlaylist(playlist: Playlist) {
        dao.insertPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        dao.deletePlaylist(playlist)
    }

    override suspend fun getPlaylistById(id: Int): Playlist {
        return dao.getPlaylistById(id)
    }

    override fun getPlaylists(): Flow<List<Playlist>> {
        return dao.getPlaylists()
    }
}