package com.example.moviecaseapp.domain.repository

import androidx.paging.PagingData
import com.example.moviecaseapp.data.remote.dto.detail.MovieDetailDto
import com.example.moviecaseapp.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun searchMovie(query: String): Flow<PagingData<SearchItem>>
    suspend fun getMovieDetail(title: String): MovieDetailDto
}