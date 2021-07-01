package com.example.flicks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {
    var movieNowPlayingData: MutableLiveData<NowPlayingMovie> = MutableLiveData()

    fun getMovieNowPlayingDataObserver() : MutableLiveData<NowPlayingMovie>{
        return movieNowPlayingData
    }

    fun makeAPICall(key: String, page: Int){
        val retroInstance = RetroInstance.getRetroInstance().create(ApiService::class.java)
        val call = retroInstance.getMovie(key,page)
        call.enqueue(object: Callback<NowPlayingMovie>{
            override fun onResponse(call: Call<NowPlayingMovie>, response: Response<NowPlayingMovie>) {
                if (response.isSuccessful){
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