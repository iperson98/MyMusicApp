package com.android.example.mymusicplaylist.data.remote

import com.google.gson.annotations.SerializedName

data class TrackSearchResponse(
    @SerializedName("track")
    val tracks: List<ApiTrack>?
)