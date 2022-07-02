package com.example.moviecaseapp.data.remote

import com.example.moviecaseapp.common.Constant
import com.example.moviecaseapp.data.remote.dto.detail.MovieDetailDto
import com.example.moviecaseapp.data.remote.dto.search.SearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("/")
    suspend fun searchMovie(
        @Query("apikey") apikey: String = Constant.API_KEY,
        @Query("type") type: String = Constant.QUERY_TYPE,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = Constant.PAGE_SIZE,
        @Query("s") searchMovie: String
    ): SearchDto

    @GET("/")
    suspend fun getMovieDetail(
        @Query("apikey") apikey: String = Constant.API_KEY,
        @Query("plot") plot: String = Constant.QUERY_PLOT,
        @Query("t") title: String
    ): MovieDetailDto
}