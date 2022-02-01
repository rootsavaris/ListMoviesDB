package com.example.moviedb.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.paging.LoadState
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.databinding.FragmentMoviesBinding
import com.example.moviedb.utils.getDivider
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import java.lang.Boolean.TRUE

class ListMoviesFragment : BaseFragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding: FragmentMoviesBinding
        get() = _binding!!

    private val viewModel: ListMoviesViewModel by viewModel()
    private var adapter: ListMoviesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListMoviesAdapter(onNavigationClick = {
            viewModel.navigateToDetail(it.id)
        }).also {
            binding.rvMovies.adapter = it.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter { it.retry() },
                footer = MovieLoadStateAdapter { it.retry() }
            )
            it.addLoadStateListener { loadState -> renderUi(loadState) }
        }
        binding.rvMovies.addItemDecoration(getDivider(requireContext()))
        binding.swipeRefresh.setOnRefreshListener { adapter?.refresh() }
        binding.btnMoviesTryAgain.setOnClickListener { adapter?.retry() }

        lifecycleScope.launchWhenStarted {
            viewModel.getMovies().collect { movies ->
                adapter?.submitData(movies)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect {
                when (it) {
                    is ListMoviesViewModel.Event.NavigateToDetail -> {
                        findNavController().navigate(
                            ListMoviesFragmentDirections.actionListMoviesFragmentToDetailFragment(
                                it.movieId
                            )
                        )
                    }
                    is ListMoviesViewModel.Event.ShowSnackBar -> {
                        Snackbar.make(binding.root, it.text, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter?.itemCount == 0

        with(binding.rvMovies) {
            isVisible = !isListEmpty || loadState.source.refresh is LoadState.NotLoading
        }

        with(binding.txtMoviesEmpty) {
            isVisible = isListEmpty
        }

        with(binding.btnMoviesTryAgain) {
            isVisible = (loadState.source.refresh is LoadState.Error).also {
                if (it == TRUE) {
                    viewModel.showSnackBar(getString(R.string.generic_error))
                }
            }
        }

        with(binding.swipeRefresh) {
            isRefreshing = loadState.source.refresh is LoadState.Loading
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        _binding = null
    }


}