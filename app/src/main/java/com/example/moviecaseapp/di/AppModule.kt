package com.example.moviecaseapp.di

import com.example.moviecaseapp.common.Constant
import com.example.moviecaseapp.data.remote.MovieApiService
import com.example.moviecaseapp.data.repository.MovieRepositoryImpl
import com.example.moviecaseapp.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApiService(): MovieApiService {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieApiService): MovieRepository {
        return MovieRepositoryImpl(api)
    }
}