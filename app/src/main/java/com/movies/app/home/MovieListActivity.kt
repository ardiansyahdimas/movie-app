package com.movies.app.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movies.app.databinding.ActivityMovieListBinding
import com.movies.app.detail.DetailMovieActivity
import com.movies.core.R
import com.movies.core.data.Resource
import com.movies.core.ui.MovieListAdapter
import com.movies.core.utils.Config
import com.movies.core.utils.Utils.updateUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMovieListBinding
    private lateinit var movieAdapter: MovieListAdapter
    private val viewModel: HomeViewModel by viewModels()
    private var currentPage = 1

    companion object {
        const val TYPE_VALUE = "value_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieAdapter = MovieListAdapter()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val type = intent?.getStringExtra(TYPE_VALUE)

        if (type != null) {
            title = when(type) {
                Config.NOW_PLAYING_MOVIES -> getString(R.string.now_playing)
                Config.POPULAR_MOVIES -> getString(R.string.popular)
                Config.TOP_RATED_MOVIES -> getString(R.string.top_rated)
                else -> getString(R.string.upcoming)
            }

            getMovies(type, currentPage)
            setupRecyclerView(type)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun getMovies(type:String, page:Int) {
        viewModel.getMovies(type, page)
        val resultValue = when(type) {
            Config.NOW_PLAYING_MOVIES -> viewModel.resultValueNowPlaying
            Config.POPULAR_MOVIES -> viewModel.resultValuePopuler
            Config.TOP_RATED_MOVIES -> viewModel.resultValueTopRated
            else -> viewModel.resultValueUpcoming
        }

        resultValue?.observe(this){result ->
            when (result) {
                is Resource.Loading -> {
                    updateUI(true, binding.progressBar)
                }
                is Resource.Success -> {
                    updateUI(false, binding.progressBar)
                    if (page > 1) {
                        result.data?.forEach {
                            movieAdapter.addItem(it)
                        }
                    } else {
                        movieAdapter.setData(result.data)
                    }
                }
                is Resource.Error -> {
                    updateUI(false, binding.progressBar)
                }
                else -> {
                    updateUI(false, binding.progressBar)
                }
            }
        }

        movieAdapter.onItemClick =  {data ->
            val intent = Intent(this, DetailMovieActivity::class.java)
            intent.putExtra(DetailMovieActivity.EXTRA_DATA, data)
            startActivity(intent)
        }

        if (page < 2) {
            with(binding.rvMovie) {
                layoutManager = GridLayoutManager(this@MovieListActivity, 2)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    private fun setupRecyclerView(type: String) {
        binding.rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                    currentPage++
                    getMovies(type, currentPage)
                }
            }
        })
    }
}