package com.android.example.mymusicplaylist.data

import com.android.example.mymusicplaylist.data.remote.ApiTrack
import com.android.example.mymusicplaylist.data.remote.AudioDbApi
import com.android.example.mymusicplaylist.data.remote.toSongEntity
import kotlinx.coroutines.flow.Flow

class MusicRepositoryImpl(private val dao: MusicDao, private val api: AudioDbApi) :
    MusicRepository {
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

    override suspend fun searchTracksByArtist(artistName: String): Result<List<ApiTrack>> {
        return try {
            val tracks = mutableSetOf<ApiTrack>()
            val artistId = api.searchArtist(artistName)
            val albums = api.getAlbumsByArtist(artistId.artists?.first()?.id ?: "")
            albums.albums?.forEach { album ->
                api.getTracksByAlbum(album.id).tracks?.forEach { track -> tracks.add(track) }
            }
            Result.success(tracks.toList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addApiTrackToPlaylist(apiTrack: ApiTrack, playlistId: Int) {
        dao.insertSong(apiTrack.toSongEntity(playlistId))
    }
}