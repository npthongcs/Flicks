package com.example.flicks.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.util.ArrayList

data class NowPlayingMovie(
    @SerializedName("results")
    val listMovie: ArrayList<Movie>
    ): java.io.Serializable
data class Movie(
    val backdrop_path: String,
    val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
): java.io.Serializable
