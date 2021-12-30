package com.ims.moviesapp_kotlin.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ims.moviesapp_kotlin.R
import com.ims.moviesapp_kotlin.data.model.EventObserver
import com.ims.moviesapp_kotlin.databinding.FragmentTvShowDetailsBinding
import com.ims.moviesapp_kotlin.databinding.FragmentTvShowsBinding
import com.ims.moviesapp_kotlin.ui.BaseFragment
import com.ims.moviesapp_kotlin.ui.viewmodel.TvShowsViewModel
import com.ims.moviesapp_kotlin.util.extension.showSnackBar


class TvShowsFragment : BaseFragment(false) {

    private val viewModel : TvShowsViewModel by viewModels()
    private lateinit var viewDataBinding: FragmentTvShowsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentTvShowsBinding.inflate(inflater, container, false)
            .apply {
                viewmodel = viewModel
                lifecycleOwner = this@TvShowsFragment.viewLifecycleOwner

            }

        return viewDataBinding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_tv_shows, container, false)
    }

    override fun setupViewModelObservers() {
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver{
            view?.showSnackBar(it)
        })
        viewModel.goToTvShowEvent.observe(
            viewLifecycleOwner,
            EventObserver{
                navigateToTvShowDetails(it.id, it.title)
            }
        )
    }

    private fun navigateToTvShowDetails(tvShowId: Int, tvShowTitle: String) {
        val action = TvShowsFragmentDirections.actionNavigationTvShowsToTVShowDetailsFragment(
            tvShowId, tvShowTitle
        )
        findNavController().navigate(action)
    }
}