package com.example.flicks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flicks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MovieOnClickListener {

    lateinit var binding: ActivityMainBinding
    private val movieAdapter: MovieAdapter = MovieAdapter()
    private var pageCount = 1
    var isLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = makeApiCall()
        viewModel.makeAPICall("c7e5ae6c59fbe02f5481d6c5d812a701",pageCount)
        setupBinding(viewModel)

        initScrollListener(viewModel)
        movieAdapter.setOnCallBackListener(this)
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
                        viewModel.makeAPICall("c7e5ae6c59fbe02f5481d6c5d812a701",pageCount)
                        val count = movieAdapter.itemCount
                        Log.d("item count",count.toString())
                        isLoad = false
                    }
                }
            }
        })
    }

    private fun setupBinding(viewModel: MainActivityViewModel) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()

        binding.rvMovie.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
        }
    }

    private fun makeApiCall() : MainActivityViewModel{
        val viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getMovieNowPlayingDataObserver().observe(this, Observer<NowPlayingMovie> {
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
        Toast.makeText(this, data.title, Toast.LENGTH_SHORT).show()
    }
}