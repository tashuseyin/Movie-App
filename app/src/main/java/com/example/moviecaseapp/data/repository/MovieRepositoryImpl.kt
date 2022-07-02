package com.example.moviecaseapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviecaseapp.data.paging.MoviePagingSource
import com.example.moviecaseapp.data.remote.MovieApiService
import com.example.moviecaseapp.data.remote.dto.detail.MovieDetailDto
import com.example.moviecaseapp.domain.model.SearchItem
import com.example.moviecaseapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApiService
) : MovieRepository {

    override suspend fun searchMovie(query: String): Flow<PagingData<SearchItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                maxSize = 100,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                MoviePagingSource(api = api, query = query)
            }
        ).flow
    }

    override suspend fun getMovieDetail(title: String): MovieDetailDto {
        return api.getMovieDetail(title = title)
    }
}