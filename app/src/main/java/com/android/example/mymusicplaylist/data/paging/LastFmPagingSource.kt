package com.android.example.mymusicplaylist.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.example.mymusicplaylist.data.remote.audio_db.ApiTrack
import com.android.example.mymusicplaylist.data.remote.last_fm.LastFmApi
import com.android.example.mymusicplaylist.data.remote.last_fm.toApiTrack
import kotlinx.coroutines.delay

class LastFmPagingSource(
    private val api: LastFmApi,
    private val artistName: String
) : PagingSource<Int, ApiTrack>() {
    override fun getRefreshKey(state: PagingState<Int, ApiTrack>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiTrack> {
        // Mimic loading for visible loading icon
        delay(2000)
        val page = params.key ?: 1

        return try {
            val response = api.getArtistTopTracks(
                artist = artistName,
                limit = params.loadSize,
                page = page
            )
            val tracks = response.topTracks?.tracks?.map { it.toApiTrack() } ?: emptyList()
            val totalPages = response.topTracks?.attr?.totalPages?.toIntOrNull() ?: 1
            LoadResult.Page(
                data = tracks,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= totalPages || tracks.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}