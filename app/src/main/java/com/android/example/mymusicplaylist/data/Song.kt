package com.android.example.mymusicplaylist.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "songs",
    primaryKeys = ["trackId", "playlistId"],
    foreignKeys = [ForeignKey(
        entity = Playlist::class,
        parentColumns = ["id"],
        childColumns = ["playlistId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("playlistId")]
)
data class Song(
    val trackId: String,
    val playlistId: Int,
    val name: String,
    val artistName: String,
    val albumName: String?,
    val thumbnailUrl: String
)
