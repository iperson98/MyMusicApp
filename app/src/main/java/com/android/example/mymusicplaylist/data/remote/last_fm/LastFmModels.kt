package com.android.example.mymusicplaylist.data.remote.last_fm

import com.google.gson.annotations.SerializedName

data class LastFmTopTracksResponse(
    @SerializedName("toptracks")
    val topTracks: LastFmTopTracks?
)

data class LastFmTopTracks(
    @SerializedName("track")
    val tracks: List<LastFmTrack>?,
    @SerializedName("@attr")
    val attr: LastFmPaginationAttr?
)

data class LastFmPaginationAttr(
    @SerializedName("artist")
    val artist: String,
    @SerializedName("page")
    val page: String,
    @SerializedName("perPage")
    val perPage: String,
    @SerializedName("totalPages")
    val totalPages: String,
    @SerializedName("total")
    val total: String,
)

data class LastFmTrack(
    @SerializedName("name")
    val name: String,
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("url")
    val url: String,
    @SerializedName("artist")
    val artist: LastFmArtist,
    @SerializedName("image")
    val images: List<LastFmImage>?,
)

data class LastFmArtist(
    @SerializedName("name")
    val name: String,
    @SerializedName("mbid")
    val mbid: String?,
)

data class LastFmImage(
    @SerializedName("#text")
    val url: String,
    @SerializedName("size")
    val size: String,
)

fun List<LastFmImage>?.getLargeImage(): String? {
    return this?.find { it.size == "extralarge" }?.url
        ?: this?.find { it.size == "large" }?.url
        ?: this?.firstOrNull()?.url
}