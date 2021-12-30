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
import com.ims.moviesapp_kotlin.databinding.FragmentPersonDetailsBinding
import com.ims.moviesapp_kotlin.ui.BaseFragment
import com.ims.moviesapp_kotlin.ui.viewmodel.PersonDetailsViewModel
import com.ims.moviesapp_kotlin.ui.viewmodel.PersonDetailsViewModelFactory
import com.ims.moviesapp_kotlin.util.extension.openUrl
import com.ims.moviesapp_kotlin.util.extension.showSnackBar


class PersonDetailsFragment : BaseFragment(true) {

    private val args: PersonDetailsFragmentArgs by navArgs()
    private val viewModel: PersonDetailsViewModel by viewModels { PersonDetailsViewModelFactory(args.personIdArg) }
    private lateinit var viewDataBinding: FragmentPersonDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentPersonDetailsBinding.inflate(inflater, container, false)
                .apply {
                    viewmodel = viewModel
                    lifecycleOwner = this@PersonDetailsFragment.viewLifecycleOwner
                }
        return viewDataBinding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_person_details, container, false)
    }

    override fun setupViewModelObservers() {
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver { view?.showSnackBar(it) })
        viewModel.goToImageEvent.observe(
            viewLifecycleOwner,
            EventObserver { openUrl(TheMovieDatabaseAPI.getProfileUrl(it.filePath)) })

        viewModel.goToCreditEvent.observe(viewLifecycleOwner, EventObserver {
            if (it.mediaType == "movie") {
                navigateToMovieDetails(it.id, it.title)
            } else if (it.mediaType == "tv") {
                navigateToTvShowDetails(it.id, it.title)
            }
        })
    }

    private fun navigateToMovieDetails(id: Int, title: String) {
        val action =
            PersonDetailsFragmentDirections.actionPersonDetailsFragmentToMovieDetailsFragment(
                id,
                title
            )
        findNavController().navigate(action)
    }

    private fun navigateToTvShowDetails(id: Int, title: String) {
        val action =
            PersonDetailsFragmentDirections.actionPersonDetailsFragmentToTvShowDetailsFragment(
                id,
                title
            )
        findNavController().navigate(action)
    }


}