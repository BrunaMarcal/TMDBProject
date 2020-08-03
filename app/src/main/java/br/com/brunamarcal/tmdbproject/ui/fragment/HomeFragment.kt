package br.com.brunamarcal.tmdbproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import br.com.brunamarcal.tmdbproject.BuildConfig
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.core.Status
import br.com.brunamarcal.tmdbproject.ui.activity.detail.DetailActivity
import br.com.brunamarcal.tmdbproject.ui.activity.home.HomeActivity
import br.com.brunamarcal.tmdbproject.ui.activity.home.viewmodel.HomeViewModel
import br.com.brunamarcal.tmdbproject.ui.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).viewModel

        viewModel.getMovie(BuildConfig.API_KEY, "pt-BR", false)

        viewModel.movieResponse.observe(viewLifecycleOwner, Observer { movieResponse ->
            when (movieResponse.status) {
                Status.SUCCESS -> {
                    movieResponse.data?.let {
                        with(recyclerHome) {
                            layoutManager = GridLayoutManager(context, 2)
                            setHasFixedSize(true)
                            adapter = MovieAdapter(it.movieResult) {movie ->
                                val intent = Intent(context, DetailActivity::class.java)
                                intent.putExtra("DATA_MOVIE", movie)
                                startActivity(intent)
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    Log.d("HOME_FRAGMENT", "Error -> ${movieResponse.errorMessage}")

                }
                Status.LOADING -> {
                    progressBarHome.visibility = if (movieResponse.loading == true) VISIBLE else GONE
                }
            }
        })


    }
}