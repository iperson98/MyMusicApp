package com.android.example.mymusicplaylist.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface AudioDbApi {
    companion object {
        const val BASE_URL = "https://www.theaudiodb.com/api/v1/json/123/"
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
}