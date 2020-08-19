package br.com.brunamarcal.tmdbproject.ui.fragment.horror

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import br.com.brunamarcal.tmdbproject.BuildConfig
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.core.Status
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.ui.activity.detail.DetailActivity
import br.com.brunamarcal.tmdbproject.ui.activity.home.HomeActivity
import br.com.brunamarcal.tmdbproject.ui.activity.home.viewmodel.HomeViewModel
import br.com.brunamarcal.tmdbproject.ui.adapter.MovieAdapter
import br.com.brunamarcal.tmdbproject.ui.fragment.action.viewmodel.ActionViewModel
import br.com.brunamarcal.tmdbproject.ui.fragment.horror.viewmodel.HorrorViewModel
import kotlinx.android.synthetic.main.fragment_comedy.*
import kotlinx.android.synthetic.main.fragment_horror.*
import kotlinx.coroutines.Dispatchers

class HorrorFragment: Fragment(R.layout.fragment_horror) {
    lateinit var viewModel: HorrorViewModel
    lateinit var repository: Repository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {

            repository = Repository(it)
            viewModel = HorrorViewModel.HorrorViewModelProviderFactory(repository, Dispatchers.IO).create(HorrorViewModel::class.java)

            viewModel.getMovieGenreHorror(BuildConfig.API_KEY, 27, "pt-BR")

            viewModel.responseHorror.observe(viewLifecycleOwner, Observer { movieResponse ->
                progressBarHorror.visibility = if (movieResponse.loading == true) View.VISIBLE else View.GONE
                when (movieResponse.status) {
                    Status.SUCCESS -> {
                        movieResponse.data?.let {
                            with(recyclerHorror) {
                                layoutManager = GridLayoutManager(context, 2)
                                setHasFixedSize(true)
                                adapter = MovieAdapter(it.movieResult) {
                                    val intent = Intent(context, DetailActivity::class.java)
                                    intent.putExtra("DATA_MOVIE", it)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                    Status.ERROR -> {
                        Log.d("HORROR_FRAGMENT", "Error -> ${movieResponse.errorMessage}")
                    }
                    Status.LOADING -> {
                        progressBarHorror.visibility = if (movieResponse.loading == true) View.VISIBLE else View.GONE
                    }
                }
            })
        }

        }



}