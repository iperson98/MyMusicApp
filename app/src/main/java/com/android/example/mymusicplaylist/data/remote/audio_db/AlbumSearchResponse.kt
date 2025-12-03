package com.android.example.mymusicplaylist.data.remote.audio_db

import com.google.gson.annotations.SerializedName

data class AlbumSearchResponse(
    @SerializedName("album")
    val albums: List<ApiAlbum>?
)
