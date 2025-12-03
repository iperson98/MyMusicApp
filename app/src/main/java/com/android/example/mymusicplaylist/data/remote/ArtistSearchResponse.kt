package com.android.example.mymusicplaylist.data.remote

import com.google.gson.annotations.SerializedName

data class ArtistSearchResponse(
    @SerializedName("artists")
    val artists: List<ApiArtist>?
)
