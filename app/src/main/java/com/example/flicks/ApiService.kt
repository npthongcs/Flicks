package com.example.flicks

import com.example.flicks.model.NowPlayingMovie
import com.example.flicks.model.Trailer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("3/movie/now_playing")
    fun getMovie(@Query("api_key") key:String, @Query("page") page:Int): Call<NowPlayingMovie>

    @GET("3/movie/{movie_id}/trailers")
    fun getTrailer(@Path("movie_id") id: Int , @Query("api_key") key: String): Call<Trailer>
}