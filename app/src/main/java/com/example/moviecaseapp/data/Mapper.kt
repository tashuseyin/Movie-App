package com.example.moviecaseapp.data

import com.example.moviecaseapp.data.remote.dto.detail.MovieDetailDto
import com.example.moviecaseapp.data.remote.dto.search.Search
import com.example.moviecaseapp.domain.model.MovieItem
import com.example.moviecaseapp.domain.model.SearchItem

fun Search.toDomain(): SearchItem {
    return SearchItem(
        poster = poster,
        title = title,
        type = type,
        year = year
    )
}

fun MovieDetailDto.toDomain(): MovieItem {
    return MovieItem(
        actors = actors,
        awards = awards,
        country = country,
        director = director,
        genre = genre,
        language = language,
        plot = plot,
        poster = poster,
        released = released,
        runtime = runtime,
        title = title,
        type = type,
        writer = writer,
        imdbRating = imdbRating
    )
}