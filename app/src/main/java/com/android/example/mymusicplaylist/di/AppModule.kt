package com.android.example.mymusicplaylist.di

import android.app.Application
import androidx.room.Room
import com.android.example.mymusicplaylist.data.MusicDatabase
import com.android.example.mymusicplaylist.data.MusicRepository
import com.android.example.mymusicplaylist.data.MusicRepositoryImpl
import com.android.example.mymusicplaylist.data.remote.AudioDbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
    fun provideMusicRepository(db: MusicDatabase, api: AudioDbApi): MusicRepository {
        return MusicRepositoryImpl(db.dao, api)
    }
}