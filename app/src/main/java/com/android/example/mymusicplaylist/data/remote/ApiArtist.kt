package com.android.example.mymusicplaylist.data.remote

import com.google.gson.annotations.SerializedName

data class ApiArtist(
    @SerializedName("idArtist")
    val id: String
)
