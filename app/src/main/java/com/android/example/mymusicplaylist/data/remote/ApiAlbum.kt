package com.android.example.mymusicplaylist.data.remote

import com.google.gson.annotations.SerializedName

data class ApiAlbum(
    @SerializedName("idAlbum")
    val id: String
)
