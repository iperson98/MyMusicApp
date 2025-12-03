package com.android.example.mymusicplaylist.data.remote.audio_db

import com.google.gson.annotations.SerializedName

data class TrackSearchResponse(
    @SerializedName("track")
    val tracks: List<ApiTrack>?
)