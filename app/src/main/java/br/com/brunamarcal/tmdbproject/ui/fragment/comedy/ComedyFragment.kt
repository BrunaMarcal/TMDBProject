package br.com.brunamarcal.tmdbproject.ui.fragment.comedy

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
import br.com.brunamarcal.tmdbproject.ui.adapter.MovieAdapter
import br.com.brunamarcal.tmdbproject.ui.fragment.comedy.viewmodel.ComedyViewModel
import kotlinx.android.synthetic.main.fragment_comedy.*
import kotlinx.coroutines.Dispatchers


class ComedyFragment: Fragment (R.layout.fragment_comedy){
    lateinit var viewModel: ComedyViewModel
    lateinit var repository: Repository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {

            repository = Repository(it)
            viewModel = ComedyViewModel.ComedyViewModelProviderFactory(repository, Dispatchers.IO).create(ComedyViewModel::class.java)

            viewModel.getMovieGenreComedy(BuildConfig.API_KEY, 35, "pt-BR")

            viewModel.responseComedy.observe(viewLifecycleOwner, Observer() { movieResponse ->
                progressBarComedy.visibility = if (movieResponse.loading == true) View.VISIBLE else View.GONE
                when (movieResponse.status) {
                    Status.SUCCESS -> {
                        movieResponse.data?.let {
                            with(recyclerComedy) {
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
                        Log.d("COMEDY_FRAGMENT", "Error -> ${movieResponse.errorMessage}")
                    }
                    Status.LOADING -> {
                        progressBarComedy.visibility = if (movieResponse.loading == true) View.VISIBLE else View.GONE
                    }
                }
            })
        }



        }


}