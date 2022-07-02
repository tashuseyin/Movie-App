package com.example.moviecaseapp.presentation.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecaseapp.common.Resource
import com.example.moviecaseapp.data.toDomain
import com.example.moviecaseapp.domain.model.MovieItem
import com.example.moviecaseapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private var job: Job? = null
    private val _movieDetail: MutableLiveData<Resource<MovieItem>> = MutableLiveData()
    val movieDetail get() = _movieDetail


    fun getMovieDetail(title: String) {
        job?.cancel()
        job = viewModelScope.launch {
            _movieDetail.value = Resource.Loading()
            try {
                val response = repository.getMovieDetail(title)
                _movieDetail.value = Resource.Success(data = response.toDomain())
            } catch (e: HttpException) {
                _movieDetail.value =
                    Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
            } catch (e: IOException) {
                _movieDetail.value =
                    Resource.Error("Couldn't reach server. Check your internet connection.")
            }
        }
    }
}