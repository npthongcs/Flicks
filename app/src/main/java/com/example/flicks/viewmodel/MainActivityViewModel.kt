package com.example.flicks.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flicks.ApiService
import com.example.flicks.RetroInstance
import com.example.flicks.activity.MainActivity
import com.example.flicks.model.Movie
import com.example.flicks.model.NowPlayingMovie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {
    var movieNowPlayingData: MutableLiveData<NowPlayingMovie> = MutableLiveData()
    var lMovie: ArrayList<Movie> = ArrayList()

    fun getMovieNowPlayingDataObserver() : MutableLiveData<NowPlayingMovie>{
        return movieNowPlayingData
    }

    fun makeAPICall(key: String, page: Int){
        val retroInstance = RetroInstance.getRetroInstance().create(ApiService::class.java)
        val call = retroInstance.getMovie(key,page)
        call.enqueue(object: Callback<NowPlayingMovie>{
            override fun onResponse(call: Call<NowPlayingMovie>, response: Response<NowPlayingMovie>) {
                if (response.isSuccessful){
                    response.body()?.let { lMovie.addAll(it.listMovie) }
                    //Log.d("model view",response.body()?.listMovie.toString())
                    movieNowPlayingData.postValue(response.body())
                } else {
                    movieNowPlayingData.postValue(null)
                }
            }

            override fun onFailure(call: Call<NowPlayingMovie>, t: Throwable) {
                movieNowPlayingData.postValue(null)
            }

        })
    }

}
