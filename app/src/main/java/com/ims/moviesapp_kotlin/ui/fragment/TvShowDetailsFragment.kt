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
import com.ims.moviesapp_kotlin.databinding.FragmentTvShowDetailsBinding
import com.ims.moviesapp_kotlin.ui.BaseFragment
import com.ims.moviesapp_kotlin.ui.viewmodel.TVShowDetailsViewModel
import com.ims.moviesapp_kotlin.ui.viewmodel.TVShowDetailsViewModelFactory
import com.ims.moviesapp_kotlin.util.extension.openUrl
import com.ims.moviesapp_kotlin.util.extension.showSnackBar


class TvShowDetailsFragment : BaseFragment(true) {

    private val args : TvShowDetailsFragmentArgs by navArgs()
    private val viewModel : TVShowDetailsViewModel by viewModels {
        TVShowDetailsViewModelFactory(args.tvShowIdArg)
    }
   private lateinit var viewDataBinding : FragmentTvShowDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentTvShowDetailsBinding.inflate(inflater, container, false)
                .apply {
                    viewmodel = viewModel
                    lifecycleOwner = this@TvShowDetailsFragment.viewLifecycleOwner
                }
        return viewDataBinding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_tv_show_details, container, false)
    }
    override fun setupViewModelObservers() {
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver { view?.showSnackBar(it) })
        viewModel.goToCastDetailsEvent.observe(
            viewLifecycleOwner,
            EventObserver { navigateToPersonDetails(it.id, it.name) })
        viewModel.goToVideoEvent.observe(
            viewLifecycleOwner,
            EventObserver { openUrl(TheMovieDatabaseAPI.getYoutubeWatchUrl(it.key)) })
    }

    private fun navigateToPersonDetails(personId: Int, personName: String) {
        val action =
            TvShowDetailsFragmentDirections.actionTvShowDetailsFragmentToPersonDetailsFragment(
                personId,
                personName
            )
        findNavController().navigate(action)
    }
}