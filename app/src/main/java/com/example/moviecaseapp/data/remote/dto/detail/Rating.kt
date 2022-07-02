package com.example.moviecaseapp.data.remote.dto.detail

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("Source")
    val source: String? = "",
    @SerializedName("Value")
    val value: String? = ""
)