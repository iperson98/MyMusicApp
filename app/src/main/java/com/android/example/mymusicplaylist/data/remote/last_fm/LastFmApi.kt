package com.android.example.mymusicplaylist.data.remote.last_fm

import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApi {
    companion object {
        const val BASE_URL = "https://ws.audioscrobbler.com/2.0/"
        const val API_KEY = "13acc55d3e6e08ed6f17333bf8909a8a"
    }

    @GET(".")
    suspend fun getArtistTopTracks(
        @Query("method") method: String = "artist.gettoptracks",
        @Query("artist") artist: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("page") page: Int = 1
    ): LastFmTopTracksResponse
}