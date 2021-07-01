package com.example.flicks

import android.graphics.drawable.ClipDrawable.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flicks.databinding.ActivityMainBinding
import java.io.Console

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var recyclerView: RecyclerView
    private var pageCount = 1
    var isLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = makeApiCall()
        viewModel.makeAPICall("c7e5ae6c59fbe02f5481d6c5d812a701",pageCount)
        setupBinding(viewModel)
        Log.d("hahahaha",viewModel.getItemSize().toString())
        recyclerView = findViewById(R.id.rvMovie)
        //initScrollListener()
    }

//    private fun initScrollListener() {
//        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
//                if (!isLoad){
//                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == movieAdapter.itemCount-1){
//
//                    }
//                }
//            }
//        })
//    }

    private fun setupBinding(viewModel: MainActivityViewModel) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()

        binding.rvMovie.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun makeApiCall() : MainActivityViewModel{
        val viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getMovieNowPlayingDataObserver().observe(this, Observer<NowPlayingMovie> {
            if (it!=null) {
                viewModel.setAdapterData(it.listMovie)
            } else {
                Log.d("error","MakeApiCall")
            }
        })
        return viewModel
    }
}