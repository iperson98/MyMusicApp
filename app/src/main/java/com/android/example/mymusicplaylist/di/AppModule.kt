package com.android.example.mymusicplaylist.di

import android.app.Application
import androidx.room.Room
import com.android.example.mymusicplaylist.data.MusicDao
import com.android.example.mymusicplaylist.data.MusicDatabase
import com.android.example.mymusicplaylist.data.MusicRepository
import com.android.example.mymusicplaylist.data.MusicRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMusicDatabase(app: Application): MusicDatabase {
        return Room.databaseBuilder(app, MusicDatabase::class.java, "music_db").build()
    }

    @Provides
    @Singleton
    fun provideMusicRepository(db: MusicDatabase): MusicRepository {
        return MusicRepositoryImpl(db.dao)
    }
}