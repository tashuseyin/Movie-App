package com.example.moviecaseapp.presentation.ui.search.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.moviecaseapp.common.extension.loadImageView
import com.example.moviecaseapp.common.extension.placeholderProgressBar
import com.example.moviecaseapp.databinding.SearchItemCardBinding
import com.example.moviecaseapp.domain.model.SearchItem

class SearchViewHolder(private val binding: SearchItemCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(searchItem: SearchItem, onItemClickListener: ((String) -> Unit)? = null) {
        binding.apply {
            searchMovieImage.loadImageView(
                searchItem.poster,
                progressDrawable = placeholderProgressBar(searchMovieImage.context)
            )
            searchMovieTitle.text = searchItem.title
            searchMovieDate.text = searchItem.year
            searchMovieType.text = searchItem.type
        }

        binding.movieCard.setOnClickListener {
            onItemClickListener?.invoke(searchItem.title.toString())
        }
    }
}


