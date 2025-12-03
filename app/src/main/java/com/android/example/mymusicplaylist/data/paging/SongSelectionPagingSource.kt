package com.android.example.mymusicplaylist.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.example.mymusicplaylist.data.remote.audio_db.ApiTrack
import kotlinx.coroutines.delay

class SongSelectionPagingSource(
    private val tracks: List<ApiTrack>
) : PagingSource<Int, ApiTrack>() {

    override fun getRefreshKey(state: PagingState<Int, ApiTrack>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiTrack> {
        // API fetches all 100+ tracks at once
        // This helps mimic loading from API
        delay(2000)
        val page = params.key ?: 0
        val pageSize = params.loadSize

        val fromIndex = page * pageSize
        val toIndex = minOf(fromIndex + pageSize, tracks.size)
        return if (fromIndex > tracks.size) {
            LoadResult.Page(
                data = emptyList(),
                prevKey = if (page > 0) page - 1 else null,
                nextKey = null
            )
        } else {
            LoadResult.Page(
                data = tracks.subList(fromIndex, toIndex),
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (toIndex < tracks.size) page + 1 else null
            )
        }
    }

}