package com.example.moviecaseapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviecaseapp.common.Constant
import com.example.moviecaseapp.data.remote.MovieApiService
import com.example.moviecaseapp.data.toDomain
import com.example.moviecaseapp.domain.model.SearchItem

class MoviePagingSource(
    private val api: MovieApiService,
    private val query: String
) : PagingSource<Int, SearchItem>() {

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        val currentPage = params.key ?: Constant.STARTING_PAGE_INDEX
        return try {
            val response =
                api.searchMovie(page = currentPage, pageSize = params.loadSize, searchMovie = query)
            val searchList = response.search
            LoadResult.Page(
                data = searchList!!.map { it.toDomain() },
                prevKey = if (currentPage == Constant.STARTING_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (searchList.isEmpty()) null else currentPage + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}