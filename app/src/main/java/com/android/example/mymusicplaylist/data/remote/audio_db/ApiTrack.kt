package com.android.example.mymusicplaylist.data.remote.audio_db

import com.google.gson.annotations.SerializedName

data class ApiTrack(
    @SerializedName("idTrack")
    val id: String,
    @SerializedName("strTrack")
    val name: String,
    @SerializedName("strArtist")
    val artistName: String,
    @SerializedName("strAlbum")
    val albumName: String?,
    @SerializedName("strTrackThumb")
    val thumbnail: String?
)
