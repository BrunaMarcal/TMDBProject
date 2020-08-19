package br.com.brunamarcal.tmdbproject.ui.fragment.action

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
import br.com.brunamarcal.tmdbproject.ui.fragment.action.viewmodel.ActionViewModel
import kotlinx.android.synthetic.main.fragment_action.*
import kotlinx.coroutines.Dispatchers

class ActionFragment : Fragment(R.layout.fragment_action) {
    lateinit var viewModel: ActionViewModel
    lateinit var repository: Repository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {

            repository = Repository(it)
            viewModel = ActionViewModel.ActionViewModelProviderFactory(repository, Dispatchers.IO).create(ActionViewModel::class.java)

            viewModel.getMoviesGenres(BuildConfig.API_KEY, 28, "pt-BR")

            viewModel.responseAction.observe(viewLifecycleOwner, Observer() { movieResponse ->
                progressBarAction.visibility = if (movieResponse.loading == true) View.VISIBLE else View.GONE
                when (movieResponse.status) {
                    Status.SUCCESS -> {
                        movieResponse.data?.let {
                            with(recyclerAction) {
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
                        Log.d("ACTION_FRAGMENT", "Error -> ${movieResponse.errorMessage}")
                    }
                    Status.LOADING -> {
                        progressBarAction.visibility = if (movieResponse.loading == true) View.VISIBLE else View.GONE
                    }
                }
            })
        }
        }

}