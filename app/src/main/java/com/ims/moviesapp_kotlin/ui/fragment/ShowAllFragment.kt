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
import com.ims.moviesapp_kotlin.data.model.EventObserver
import com.ims.moviesapp_kotlin.databinding.FragmentShowAllBinding
import com.ims.moviesapp_kotlin.ui.BaseFragment
import com.ims.moviesapp_kotlin.ui.viewmodel.ShowAllViewModel
import com.ims.moviesapp_kotlin.ui.viewmodel.ShowAllViewModelFactory
import com.ims.moviesapp_kotlin.util.extension.showSnackBar


class ShowAllFragment : BaseFragment(true) {

    private val args : ShowAllFragmentArgs by navArgs()
    private val viewModel : ShowAllViewModel by viewModels{
        ShowAllViewModelFactory(args.movieListTypeArg)
    }
    private lateinit var viewDataBinding : FragmentShowAllBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentShowAllBinding.inflate(inflater, container, false)
            .apply {
                viewmodel = viewModel
                lifecycleOwner = this@ShowAllFragment.viewLifecycleOwner
            }

        return viewDataBinding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_show_all, container, false)
    }

    override fun setupViewModelObservers() {
//        super.setupViewModelObservers()
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver{
            view?.showSnackBar(it)
        })
        viewModel.goToMovieDetailsEvent.observe(viewLifecycleOwner, EventObserver{
            navigateToMovieDetails(it.id, it.title)
        })
    }

    private fun navigateToMovieDetails(movieId: Int, movieTitle: String) {
        val action = ShowAllFragmentDirections.actionShowAllFragmentToMovieDetailsFragment(
            movieId,
            movieTitle
        )
        findNavController().navigate(action)
    }

}