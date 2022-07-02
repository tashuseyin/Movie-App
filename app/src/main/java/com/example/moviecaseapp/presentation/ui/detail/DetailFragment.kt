package com.example.moviecaseapp.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.moviecaseapp.common.Resource
import com.example.moviecaseapp.common.binding_adapter.BindingFragment
import com.example.moviecaseapp.common.extension.loadImageView
import com.example.moviecaseapp.common.extension.placeholderProgressBar
import com.example.moviecaseapp.databinding.FragmentDetailBinding
import com.example.moviecaseapp.domain.model.MovieItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BindingFragment<FragmentDetailBinding>() {
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDetailBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestMovieDetail()
        observeViewModel()
    }

    private fun requestMovieDetail() {
        args.movieTitle?.let {
            detailViewModel.getMovieDetail(it)
        }
    }

    private fun observeViewModel() {
        detailViewModel.movieDetail.observe(viewLifecycleOwner) { result ->
            binding.apply {
                when (result) {
                    is Resource.Loading -> {
                        progressBar.isVisible = true
                        scrollView.isVisible = false
                    }
                    is Resource.Success -> {
                        progressBar.isVisible = false
                        scrollView.isVisible = true
                        result.data?.let {
                            initUi(it)
                        }
                    }
                    is Resource.Error -> {
                        progressBar.isVisible = false
                        errorText.text = result.message
                    }
                }
            }
        }
    }

    private fun initUi(movieItem: MovieItem) {
        binding.apply {
            movieItem.also {
                binding.movieDetailImage.loadImageView(
                    it.poster,
                    placeholderProgressBar(requireContext())
                )
                moviePlot.text = it.plot
                movieImdb.text = it.imdbRating
                movieRuntime.text = it.runtime
                movieYear.text = it.released
                movieName.text = it.title
                movieActors.text = it.actors
                movieAwards.text = it.awards
                movieLanguage.text = it.language
                movieGenre.text = it.genre
                movieDirector.text = it.director
                movieWriter.text = it.writer
            }
        }
    }
}



