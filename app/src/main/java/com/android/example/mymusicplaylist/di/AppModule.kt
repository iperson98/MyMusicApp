package com.android.example.mymusicplaylist.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.example.mymusicplaylist.data.MusicDatabase
import com.android.example.mymusicplaylist.data.MusicRepository
import com.android.example.mymusicplaylist.data.MusicRepositoryImpl
import com.android.example.mymusicplaylist.data.remote.audio_db.AudioDbApi
import com.android.example.mymusicplaylist.data.remote.last_fm.LastFmApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("last_fm")
    fun provideLastFmRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(LastFmApi.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideLastFmApi(@Named("last_fm") retrofit: Retrofit): LastFmApi {
        return retrofit.create(LastFmApi::class.java)
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE songs ADD COLUMN albumName TEXT")
        }
    }

    @Provides
    @Singleton
    fun provideMusicDatabase(app: Application): MusicDatabase {
        return Room.databaseBuilder(app, MusicDatabase::class.java, "music_db")
            .addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AudioDbApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAudioDbApi(retrofit: Retrofit): AudioDbApi {
        return retrofit.create(AudioDbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMusicRepository(
        db: MusicDatabase,
        audioDbApi: AudioDbApi,
        lastFmApi: LastFmApi
    ): MusicRepository {
        return MusicRepositoryImpl(db.dao, audioDbApi, lastFmApi)
    }
}