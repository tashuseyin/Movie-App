package com.example.moviecaseapp.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.viewbinding.ViewBinding
import com.example.moviecaseapp.R
import com.example.moviecaseapp.common.binding_adapter.BindingFragment
import com.example.moviecaseapp.common.extension.hideKeyboard
import com.example.moviecaseapp.databinding.FragmentSearchBinding
import com.example.moviecaseapp.presentation.ui.search.adapter.LoadingStateAdapter
import com.example.moviecaseapp.presentation.ui.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(), SearchView.OnQueryTextListener {
    private val searchViewModel: SearchViewModel by viewModels()
    private val adapter = SearchAdapter()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSearchBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateDetailFragment()
        observeUi()
        initAdapter()
        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(this)
    }

    private fun navigateDetailFragment() {
        adapter.onItemClickListener = { movieTitle ->
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                    movieTitle
                )
            )
        }
    }

    private fun initAdapter() {
        searchViewModel.movieList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }

    private fun observeUi() {
        binding.recyclerView.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter { adapter.retry() }
        )
        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                binding.apply {
                    recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                    progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                    handleError(loadState)
                }
            }
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        binding.apply {
            if (loadState.source.refresh is LoadState.Error) {
                errorText.isVisible = loadState.source.refresh is LoadState.Error
                errorText.text =
                    (loadState.source.refresh as LoadState.Error).error.localizedMessage
            } else if (loadState.source.refresh is LoadState.NotLoading && searchViewModel.searchQuery.isNotBlank() && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                errorText.isVisible = true
                errorText.text = getString(R.string.movie_not_found)
                emptyAnimation.isVisible = true
            } else {
                errorText.isVisible = false
                emptyAnimation.isVisible = false
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            view?.let { hideKeyboard(view = it) }
            searchViewModel.searchQuery = query
            searchViewModel.searchMovies(query)
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }
}
