package com.example.moviecaseapp.data.remote.dto.search

import com.google.gson.annotations.SerializedName

data class SearchDto(
    @SerializedName("response")
    val response: String? = "",
    @SerializedName("Search")
    val search: List<Search>? = listOf(),
    @SerializedName("totalResults")
    val totalResults: String? = ""
)