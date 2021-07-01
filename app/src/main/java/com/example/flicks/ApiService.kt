package com.example.flicks

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("3/movie/now_playing")
    fun getMovie(@Query("api_key") key:String, @Query("page") page:Int): Call<NowPlayingMovie>
}