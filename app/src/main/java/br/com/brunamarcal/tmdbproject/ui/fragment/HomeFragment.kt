package br.com.brunamarcal.tmdbproject.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.brunamarcal.tmdbproject.BuildConfig
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.core.Status
import br.com.brunamarcal.tmdbproject.ui.activity.home.HomeActivity
import br.com.brunamarcal.tmdbproject.ui.activity.home.viewmodel.HomeViewModel

class HomeFragment: Fragment(R.layout.fragment_home){
    lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).viewModel


        viewModel.getMovie(BuildConfig.API_KEY, "pt-BR",false)

        viewModel.movieResponse.observe(viewLifecycleOwner, Observer { movieResponse ->
            when(movieResponse.status){
                Status.SUCCESS -> {

                }
            }
        })




    }
}