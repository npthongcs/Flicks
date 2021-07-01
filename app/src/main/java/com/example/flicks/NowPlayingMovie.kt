package com.example.flicks

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class NowPlayingMovie(
    @SerializedName("results")
    val listMovie: ArrayList<Movie>
    )
data class Movie(
    val backdrop_path: String,
    val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)
