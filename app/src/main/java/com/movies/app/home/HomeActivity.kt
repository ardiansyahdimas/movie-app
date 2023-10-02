package com.movies.app.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.app.databinding.ActivityHomeBinding
import com.movies.app.detail.DetailMovieActivity
import com.movies.core.data.Resource
import com.movies.core.ui.MovieAdapter
import com.movies.core.utils.Config
import com.movies.core.utils.Utils.updateUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var nowPlayingAdapter: MovieAdapter
    private lateinit var populerAdapter: MovieAdapter
    private lateinit var topRatedAdapter: MovieAdapter
    private lateinit var upcomingAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nowPlayingAdapter = MovieAdapter()
        populerAdapter = MovieAdapter()
        topRatedAdapter = MovieAdapter()
        upcomingAdapter = MovieAdapter()

        getMovies(Config.NOW_PLAYING_MOVIES)
        getMovies(Config.POPULAR_MOVIES)
        getMovies(Config.TOP_RATED_MOVIES)
        getMovies(Config.UPCOMING_MOVIES)

        seeMore()
    }

    private fun getMovies(type:String) {
        viewModel.getMovies(type, 1)

        val resultValue = when(type) {
            Config.NOW_PLAYING_MOVIES -> viewModel.resultValueNowPlaying
            Config.POPULAR_MOVIES -> viewModel.resultValuePopuler
            Config.TOP_RATED_MOVIES -> viewModel.resultValueTopRated
            else -> viewModel.resultValueUpcoming
        }


        val movieAdapter = when (type){
            Config.NOW_PLAYING_MOVIES -> nowPlayingAdapter
            Config.POPULAR_MOVIES -> populerAdapter
            Config.TOP_RATED_MOVIES -> topRatedAdapter
            else -> upcomingAdapter
        }

        val recylerview = when (type){
            Config.POPULAR_MOVIES -> binding.rvPopuler
            Config.TOP_RATED_MOVIES -> binding.rvTopRated
            else -> binding.rvUpcoming
        }

        val progressBar = when (type){
            Config.NOW_PLAYING_MOVIES -> binding.progressBar
            Config.POPULAR_MOVIES -> binding.progressBar1
            Config.TOP_RATED_MOVIES -> binding.progressBar2
            else -> binding.progressBar3
        }

        resultValue?.observe(this){result ->
            when (result) {
                is Resource.Loading -> updateUI(true, progressBar)
                is Resource.Success -> {
                    updateUI(false, progressBar)
                    movieAdapter.setData(result.data)
                }
                is Resource.Error -> {
                    updateUI(false, progressBar)
                }
                else -> {
                    updateUI(false, progressBar)
                }
            }
        }

        movieAdapter.onItemClick =  {data ->
            val intent = Intent(this, DetailMovieActivity::class.java)
            intent.putExtra(DetailMovieActivity.EXTRA_DATA, data)
            startActivity(intent)
        }

        when (type) {
            Config.NOW_PLAYING_MOVIES ->{
                binding.rvNowPlaying.adapter = nowPlayingAdapter
                binding.rvNowPlaying.apply {
                    set3DItem(true)
                    setInfinite(true)
                }
            }
            else -> {
                with(recylerview) {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                    adapter = movieAdapter
                }
            }
        }
    }

    private fun seeMore() {
        binding.apply {
            titleNowPlaying.setOnClickListener { intentToMovieList(Config.NOW_PLAYING_MOVIES) }
            titlePopular.setOnClickListener { intentToMovieList(Config.POPULAR_MOVIES) }
            titleTopRated.setOnClickListener { intentToMovieList(Config.TOP_RATED_MOVIES) }
            titleUpcoming.setOnClickListener {intentToMovieList(Config.UPCOMING_MOVIES) }
        }
    }

    private fun intentToMovieList(type: String){
        val intent = Intent(this@HomeActivity, MovieListActivity::class.java)
        intent.putExtra(MovieListActivity.TYPE_VALUE, type)
        startActivity(intent)
    }
}