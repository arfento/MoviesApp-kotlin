package com.ims.moviesapp_kotlin.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ims.moviesapp_kotlin.R
import com.ims.moviesapp_kotlin.data.db.remote.TheMovieDatabaseAPI
import com.ims.moviesapp_kotlin.data.model.EventObserver
import com.ims.moviesapp_kotlin.databinding.FragmentMovieDetailsBinding
import com.ims.moviesapp_kotlin.ui.BaseFragment
import com.ims.moviesapp_kotlin.ui.viewmodel.MovieDetailsViewModel
import com.ims.moviesapp_kotlin.ui.viewmodel.MovieDetailsViewModelFactory
import com.ims.moviesapp_kotlin.util.extension.openUrl
import com.ims.moviesapp_kotlin.util.extension.showSnackBar

class MovieDetailsFragment : BaseFragment(true) {

    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MovieDetailsViewModel by viewModels() {
        MovieDetailsViewModelFactory(args.movieIdArg)
    }
    private lateinit var viewDataBinding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentMovieDetailsBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            lifecycleOwner = this@MovieDetailsFragment.viewLifecycleOwner
        }
        return viewDataBinding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun setupViewModelObservers() {
//        super.setupViewModelObservers()
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver{
            view?.showSnackBar(it)
        })
        viewModel.goToCastDetailsEvent.observe(
            viewLifecycleOwner,
            EventObserver{
                navigateToPersonDetails(it.id, it.name)
            }
        )
        viewModel.goToVideoEvent.observe(
            viewLifecycleOwner, EventObserver{
                openUrl(TheMovieDatabaseAPI.getYoutubeWatchUrl(it.key))
            }
        )
    }

    private fun navigateToPersonDetails(personId: Int, personName: String) {
        val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToPersonDetailsFragment(
            personId, personName
        )
        findNavController().navigate(action)
    }

}