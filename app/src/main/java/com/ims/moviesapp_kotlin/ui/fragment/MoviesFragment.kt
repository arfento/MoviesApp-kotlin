package com.ims.moviesapp_kotlin.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ims.moviesapp_kotlin.R
import com.ims.moviesapp_kotlin.data.model.EventObserver
import com.ims.moviesapp_kotlin.databinding.FragmentMoviesBinding
import com.ims.moviesapp_kotlin.ui.BaseFragment
import com.ims.moviesapp_kotlin.ui.viewmodel.MoviesViewModel
import com.ims.moviesapp_kotlin.util.extension.showSnackBar

class MoviesFragment : BaseFragment(false) {

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var viewDataBinding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentMoviesBinding.inflate(inflater, container, false)
                .apply {
                    viewmodel = viewModel
                    lifecycleOwner = this@MoviesFragment.viewLifecycleOwner
                }
        return viewDataBinding.root
    }

    override fun setupViewModelObservers() {
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver { view?.showSnackBar(it) })
        viewModel.goToMovieDetailsEvent.observe(
            viewLifecycleOwner,
            EventObserver { navigateToMovieDetails(it.id, it.title) })
    }

    private fun navigateToMovieDetails(movieId: Int, movieTitle: String) {
        val action = MoviesFragmentDirections.actionNavigationMoviesToMovieDetailsFragment(
            movieId,
            movieTitle
        )
        findNavController().navigate(action)
    }
}