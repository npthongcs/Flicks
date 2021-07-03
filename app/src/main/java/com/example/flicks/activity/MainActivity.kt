package com.example.flicks.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flicks.BR
import com.example.flicks.MovieAdapter
import com.example.flicks.MovieOnClickListener
import com.example.flicks.R
import com.example.flicks.databinding.ActivityMainBinding
import com.example.flicks.model.Movie
import com.example.flicks.model.NowPlayingMovie
import com.example.flicks.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(), MovieOnClickListener {

    lateinit var binding: ActivityMainBinding
    private val movieAdapter: MovieAdapter = MovieAdapter()
    private var pageCount = 1
    var isLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState!=null){
            pageCount = savedInstanceState.getInt("NUM_PAGE")
            Log.d("instance page",pageCount.toString())
        }

        val viewModel = makeApiCall()
        viewModel.makeAPICall("c7e5ae6c59fbe02f5481d6c5d812a701",pageCount)
        setupBinding(viewModel)

        initScrollListener(viewModel)
        movieAdapter.setOnCallBackListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("NUM_PAGE",pageCount)
    }


    private fun initScrollListener(viewModel: MainActivityViewModel) {
        binding.rvMovie.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = binding.rvMovie.layoutManager as LinearLayoutManager?
                if (!isLoad){
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == movieAdapter.itemCount-1){
                        isLoad = true
                        pageCount += 1
                        Log.d("page in scroll",pageCount.toString())
                        viewModel.makeAPICall("c7e5ae6c59fbe02f5481d6c5d812a701",pageCount)
                        isLoad = false
                    }
                }
            }
        })
    }

    private fun setupBinding(viewModel: MainActivityViewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()

        binding.rvMovie.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
        }
    }

    private fun makeApiCall() : MainActivityViewModel {
        val viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getMovieNowPlayingDataObserver().observe(this, Observer<NowPlayingMovie> {
            Log.d("message","Received view model, pageCount $pageCount")
            if (it!=null) {
                movieAdapter.setDataList(it.listMovie)
                movieAdapter.notifyDataSetChanged()
            } else {
                Log.d("error","MakeApiCall")
            }
        })
        return viewModel
    }

    override fun onItemClick(data: Movie, position: Int) {
        val i = Intent(this,DetailMovieActivity::class.java)
        i.putExtra("movie",data)
       // Toast.makeText(this, data.id.toString(), Toast.LENGTH_SHORT).show()
        startActivity(i)
    }
}

