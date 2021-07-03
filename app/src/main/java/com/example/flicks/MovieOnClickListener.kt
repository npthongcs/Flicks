package com.example.flicks

import com.example.flicks.model.Movie

interface MovieOnClickListener {
    fun onItemClick(data: Movie, position: Int)
}