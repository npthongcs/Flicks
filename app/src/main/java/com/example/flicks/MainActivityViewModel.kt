package com.example.flicks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {
    var movieNowPlayingData: MutableLiveData<NowPlayingMovie> = MutableLiveData()
    private var movieAdapter: MovieAdapter = MovieAdapter()
    var itemCount = 0

    fun getMovieNowPlayingDataObserver() : MutableLiveData<NowPlayingMovie>{
        return movieNowPlayingData
    }

    fun getAdapter() : MovieAdapter{
        return movieAdapter
    }

    fun getItemSize(): Int{
        return itemCount
    }

    fun setAdapterData(data: ArrayList<Movie>){
        movieAdapter.setDataList(data)
        itemCount = movieAdapter.itemCount
        movieAdapter.notifyDataSetChanged()
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