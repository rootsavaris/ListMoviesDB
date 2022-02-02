package com.example.moviedb.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.transform.RoundedCornersTransformation
import org.koin.android.viewmodel.ext.android.viewModel
import com.example.moviedb.databinding.FragmentMovieDetailBinding
import kotlinx.coroutines.flow.collectLatest
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.moviedb.R
import com.example.moviedb.utils.BASE_IMAGE_URL
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class DetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding: FragmentMovieDetailBinding
        get() = _binding!!

    private val viewModel: DetailMovieViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.viewState.collectLatest {
                binding.pbMovieDetail.isVisible = it.isLoading
                binding.txtDescription.isVisible = it.success
                binding.txtMovieDescription.isVisible = it.success
                binding.btnMovieTryAgain.isVisible = it.error

                if(it.success){
                    it.successValue?.let { movie ->
                        binding.txtMovieTitle.text = movie.originalTitle
                        binding.txtMovieDescription.text = movie.overview
                        binding.movieImage.load(BASE_IMAGE_URL + movie.posterPath) {
                            crossfade(durationMillis = 2000)
                            transformations(
                                RoundedCornersTransformation(0f)
                            )
                        }
                    }
                }

                if(it.error){
                    viewModel.showSnackBar(getString(R.string.generic_error))
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect {
                when (it) {
                    is DetailMovieViewModel.Event.ShowSnackBar -> {
                        Snackbar.make(binding.root, it.text, Snackbar.LENGTH_LONG).show()
                    }
                    DetailMovieViewModel.Event.NavigationBack -> findNavController().popBackStack()
                }
            }
        }

        binding.btnBack.setOnClickListener {
            viewModel.backPress()
        }

        binding.btnMovieTryAgain.setOnClickListener {
            viewModel.tryAgain(args.movieId)
        }

        viewModel.getMovie(args.movieId)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}