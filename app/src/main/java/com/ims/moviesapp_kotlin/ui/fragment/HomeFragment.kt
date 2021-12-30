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
import com.ims.moviesapp_kotlin.data.model.MovieListType
import com.ims.moviesapp_kotlin.databinding.FragmentHomeBinding
import com.ims.moviesapp_kotlin.ui.BaseFragment
import com.ims.moviesapp_kotlin.ui.viewmodel.HomeViewModel
import com.ims.moviesapp_kotlin.util.extension.showSnackBar

class HomeFragment : BaseFragment(false) {

    private val viewModel : HomeViewModel by viewModels()
    private lateinit var viewDataBinding : FragmentHomeBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            lifecycleOwner = this@HomeFragment.viewLifecycleOwner
        }

        return viewDataBinding.root

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun setupViewModelObservers() {
//        super.setupViewModelObservers()
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver{
            view?.showSnackBar(it)
        })
        viewModel.goToShowAllEvent.observe(
            viewLifecycleOwner,
            EventObserver{
                navigateShowAll(it)
            }
        )
        viewModel.goToMovieDetailsEvent.observe(
            viewLifecycleOwner,
            EventObserver{
                navigateToMovieDetails(it.id, it.title)
            }
        )
    }

    private fun navigateToMovieDetails(movieId: Int, movieTitle: String) {
        val action = HomeFragmentDirections.actionNavigationHomeToMovieDetailsFragment(movieId, movieTitle)
        findNavController().navigate(action)
    }

    private fun navigateShowAll(movieListType: MovieListType) {
        val action = HomeFragmentDirections.actionNavigationHomeToShowAllFragment(movieListType)
        findNavController().navigate(action)
    }


}