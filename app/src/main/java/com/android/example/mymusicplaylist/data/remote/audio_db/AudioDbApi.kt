package com.android.example.mymusicplaylist.data.remote.audio_db

import retrofit2.http.GET
import retrofit2.http.Query

interface AudioDbApi {
    companion object {
        const val BASE_URL = "https://www.theaudiodb.com/api/v1/json/698724/"
    }

    @GET("searchtrack.php")
    suspend fun searchTrackByName(
        @Query("s") artistName: String,
        @Query("t") trackName: String
    ): TrackSearchResponse

    @GET("track-top10.php")
    suspend fun getTopTracksByArtist(
        @Query("s") artistName: String,
    ): TrackSearchResponse

    @GET("track.php")
    suspend fun getTracksByAlbum(
        @Query("m") albumId: String
    ): TrackSearchResponse

    @GET("album.php")
    suspend fun getAlbumsByArtist(
        @Query("i") artistId: String
    ): AlbumSearchResponse

    @GET("search.php")
    suspend fun searchArtist(
        @Query("s") artistName: String
    ): ArtistSearchResponse
}