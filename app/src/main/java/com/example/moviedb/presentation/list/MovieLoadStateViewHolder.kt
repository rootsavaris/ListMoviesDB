package com.example.moviedb.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.databinding.ItemMoviesLoadStateFooterBinding

class MovieLoadStateViewHolder(
    private val binding: ItemMoviesLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnMoviesRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvMoviesErrorDescription.text = loadState.error.localizedMessage
        }
        binding.progressMoviesLoadMore.isVisible = loadState is LoadState.Loading
        binding.btnMoviesRetry.isVisible = loadState is LoadState.Error
        binding.tvMoviesErrorDescription.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): MovieLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movies_load_state_footer, parent, false)
            val binding = ItemMoviesLoadStateFooterBinding.bind(view)
            return MovieLoadStateViewHolder(binding, retry)
        }
    }
}