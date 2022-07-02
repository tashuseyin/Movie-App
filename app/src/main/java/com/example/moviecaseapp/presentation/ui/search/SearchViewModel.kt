package com.example.moviecaseapp.presentation.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviecaseapp.domain.model.SearchItem
import com.example.moviecaseapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {


    var searchQuery: String = ""

    private var job: Job? = null
    private val _movieList = MutableLiveData<PagingData<SearchItem>>(PagingData.empty())
    val movieList get() = _movieList

    fun searchMovies(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            repository.searchMovie(query = query).cachedIn(viewModelScope).distinctUntilChanged()
                .collectLatest {
                    _movieList.value = it
                }
        }
    }
}