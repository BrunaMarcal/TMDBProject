package br.com.brunamarcal.tmdbproject.ui.activity.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import br.com.brunamarcal.tmdbproject.BuildConfig
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.core.Status
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.ui.activity.home.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers

class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        viewModel = HomeViewModel.HomeViewModelProviderFactory(repository, Dispatchers.IO).create(HomeViewModel::class.java)

        viewModel.getMovie(BuildConfig.API_KEY, "pt-BR", false)

        viewModel.movieResponse.observe(this, Observer { response ->
            when (response.status){
                Status.SUCCESS -> {
                    response.data?.let { result ->
                        Log.d("HomeActivity", "Filme -> ${result.results[0].originalTitle}")
                    }
                }
                Status.ERROR -> {
                    response.errorMessage?.let {
                        Log.d("HomeActivity", "Error -> $it")
                    }
                }
                Status.LOADING -> {

                }
            }

        })
    }
}
