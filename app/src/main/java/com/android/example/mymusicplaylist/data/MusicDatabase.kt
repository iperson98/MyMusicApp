package com.android.example.mymusicplaylist.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Playlist::class, Song::class],
    version = 2
)
abstract class MusicDatabase: RoomDatabase() {
    abstract val dao: MusicDao
}