package com.movies.app.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.app.databinding.ActivityDetailMovieBinding
import com.movies.app.home.HomeViewModel
import com.movies.core.R
import com.movies.core.data.Resource
import com.movies.core.domain.model.MovieModel
import com.movies.core.ui.ReviewMovieAdapter
import com.movies.core.utils.Utils.updateUI
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var reviewMovieAdapter: ReviewMovieAdapter
    private var currentPage = 1
    private var isEmptyReview = false

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(EXTRA_DATA, MovieModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent?.getParcelableExtra(EXTRA_DATA)
        }

        if (data != null) {
            reviewMovieAdapter = ReviewMovieAdapter()
            getReviewsMovie(data.id, currentPage)
            setupRecyclerView(data.id)
            showData(data)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun showData(data:MovieModel) {
        with(binding){
            Glide.with(this@DetailMovieActivity)
                .load(getString(com.movies.core.R.string.baseUrlImage, data.poster_path))
                .into(poster)
            Glide.with(this@DetailMovieActivity)
                .load(getString(com.movies.core.R.string.baseUrlImage, data.backdrop_path))
                .into(backDrop)
            tvTitle.text = data.title
            tvRating.text = "${data.vote_average} / ${data.vote_count}"
            tvOverview.text = data.overview
            tvReleaseDate.text = "Release Date: ${data.release_date}"
            getVideosMovieByMovieId(data.id)
        }
    }

    private fun getVideosMovieByMovieId(movieId: Int){
        viewModel.getVideosMovieByMovieId(movieId)
        viewModel.resultVideosMovie?.observe(this){result ->
            when (result) {
                is Resource.Loading ->Timber.d("Loading")
                is Resource.Success -> {
                    Timber.d("Success")
                    val videoMovie = result.data?.firstOrNull{ it.site == "YouTube" && it.type == "Trailer"}
                    if (videoMovie != null) {
                       binding.backDrop.setOnClickListener{
                           openWebPage(getString(R.string.baseUrlVideo, videoMovie.key))
                       }
                    }
                }
                is Resource.Error -> {
                    Timber.d("Error ${result.message}")
                }
                else -> {
                    Timber.d("Error ${result.message}")
                }
            }
        }
    }

    private  fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(intent)
    }

    private fun getReviewsMovie(movieId: Int, page:Int) {
        viewModel.getReviewsMovieByMovieId(movieId, page)
        viewModel.resultReviewsMovie?.observe(this){result ->
            when (result) {
                is Resource.Loading -> updateUI(true, binding.progressBar)
                is Resource.Success -> {
                    isEmptyReview = result.data?.size == reviewMovieAdapter.itemCount
                    updateUI(false, binding.progressBar)
                    if (page > 1) {
                        result.data?.forEach {
                            reviewMovieAdapter.addItem(it)
                        }
                    } else {
                        reviewMovieAdapter.setData(result.data)
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

        if (page < 2) {
            with(binding.rvReview) {
                layoutManager =  GridLayoutManager(this@DetailMovieActivity, 2)
                setHasFixedSize(true)
                adapter = reviewMovieAdapter
            }
        }
    }

    private fun setupRecyclerView(movieId: Int) {
        binding.rvReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (!isEmptyReview && (visibleItemCount + firstVisibleItem) >= totalItemCount) {
                    currentPage++
                    getReviewsMovie(movieId, currentPage)
                }
            }
        })
    }
}