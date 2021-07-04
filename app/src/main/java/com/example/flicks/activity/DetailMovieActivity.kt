package com.example.flicks.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flicks.ApiService
import com.example.flicks.R
import com.example.flicks.RetroInstance
import com.example.flicks.model.Movie
import com.example.flicks.model.Trailer
import com.example.flicks.model.Youtube
import com.google.android.youtube.player.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    var id: Int = 0
    var listYoutube: ArrayList<Youtube> = ArrayList()
    var trailer: Trailer = Trailer(listYoutube)
    var movie: Movie? = null
    private var voteAverage: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val intent: Intent = intent
        movie = intent.getSerializableExtra("movie") as Movie?
        //Log.d("movie",movie.toString())

        val titleMovie = findViewById<TextView>(R.id.titleMovie)
        val dateRelease = findViewById<TextView>(R.id.dateRelease)
        val overview = findViewById<TextView>(R.id.overview)
        titleMovie.text = movie?.title
        dateRelease.text = "Release date: ${movie?.release_date}"
        overview.text = movie?.overview
        voteAverage = movie?.vote_average!!
        movie?.id?.let { makeAPICall(it,"c7e5ae6c59fbe02f5481d6c5d812a701") }
        setRatingBar()
    }

    private fun setRatingBar() {
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        ratingBar.rating = (voteAverage/2).toFloat()
    }

    private fun makeAPICall(id: Int, key:String){
        val retroInstance = RetroInstance.getRetroInstance().create(ApiService::class.java)
        val call = retroInstance.getTrailer(id,key)
        call.enqueue(object : Callback<Trailer> {
            override fun onResponse(call: Call<Trailer>, response: Response<Trailer>) {
                if (response.isSuccessful){
                    trailer = response.body()!!
                    loadVideo()
                } else {
                }
            }

            override fun onFailure(call: Call<Trailer>, t: Throwable) {
            }
        })
    }


    fun loadVideo(){
        Log.d("list youtube", trailer.listYoutube.toString())
        val youtubePlayer = findViewById<YouTubePlayerView>(R.id.youtubePlayer)
        youtubePlayer.initialize("AIzaSyDvQ4aaFXHH7yA9fIMCQocMozomOacvehk",this)
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if (!p2){
            if (voteAverage>=7.5) {
                p1?.loadVideo(trailer.listYoutube[0].source)
            } else {
                p1?.cueVideo(trailer.listYoutube[0].source)
            }
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        Log.d("Youtube","load failed")
    }
}


