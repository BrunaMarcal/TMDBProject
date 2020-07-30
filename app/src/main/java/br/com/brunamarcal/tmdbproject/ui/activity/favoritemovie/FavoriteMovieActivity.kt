package br.com.brunamarcal.tmdbproject.ui.activity.favoritemovie

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.database.modeldb.FavoriteMovie
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.ui.activity.favoritemovie.viewmodel.FavoriteMovieViewModel
import br.com.brunamarcal.tmdbproject.ui.adapter.FavoriteAdapter
import kotlinx.android.synthetic.main.activity_favorite_movie.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers

class FavoriteMovieActivity : AppCompatActivity() {

    lateinit var viewModel: FavoriteMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movie)

        toolbarMain.title = getString(R.string.my_favorite)
        setSupportActionBar(toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        val repository = Repository(this)
        viewModel = FavoriteMovieViewModel.FavoriteMovieViewModelProviderFactory(
            application,
            repository,
            Dispatchers.IO
        ).create(FavoriteMovieViewModel::class.java)

        viewModel.getFavoriteMovie("bruna@zup.com").observe(this, Observer { listFavoriteMovie ->
            listFavoriteMovie?.let { listMovie ->
                setAdapter(listMovie)
            }
        })

    }

    private fun setAdapter(listMovie: List<FavoriteMovie>) {
        with(favoriteRecyclerView) {
            layoutManager = LinearLayoutManager(this@FavoriteMovieActivity)
            setHasFixedSize(true)
            adapter = FavoriteAdapter(listMovie)
        }
    }

}





