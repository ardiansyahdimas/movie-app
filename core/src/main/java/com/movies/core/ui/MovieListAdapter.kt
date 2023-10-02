package com.movies.core.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.movies.core.R
import com.movies.core.databinding.RvMovie2Binding
import com.movies.core.domain.model.MovieModel

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.ListViewHolder>() {
    private var listData = ArrayList<MovieModel>()
    var onItemClick: ((MovieModel) -> Unit)? = null


    fun setData(newListData: List<MovieModel>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    fun addItem(item: MovieModel) {
        val position = listData.size
        listData.add(position, item)
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_movie2, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RvMovie2Binding.bind(itemView)
        fun bind(data: MovieModel) {
            with(binding) {
                tvTitle.text = data.title
                tvInfo.text = data.vote_average.toString()
                adult.isVisible = data.adult == true
                Glide.with(itemView.context)
                    .load(itemView.context.getString(R.string.baseUrlImage, data.poster_path))
                    .error(R.drawable.ic_movie)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.isVisible = false
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            dataSource: com.bumptech.glide.load.DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.isVisible = false
                            return false
                        }

                    })
                    .into(poster)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[absoluteAdapterPosition])
            }
        }
    }
}