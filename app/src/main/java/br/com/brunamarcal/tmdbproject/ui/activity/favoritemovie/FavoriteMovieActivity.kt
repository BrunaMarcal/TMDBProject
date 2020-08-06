package br.com.brunamarcal.tmdbproject.ui.activity.favoritemovie

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.core.Status
import br.com.brunamarcal.tmdbproject.data.database.modeldb.FavoriteMovie
import br.com.brunamarcal.tmdbproject.data.model.MovieResult
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.data.util.SharedPreference
import br.com.brunamarcal.tmdbproject.ui.activity.detail.DetailActivity
import br.com.brunamarcal.tmdbproject.ui.activity.favoritemovie.viewmodel.FavoriteMovieViewModel
import br.com.brunamarcal.tmdbproject.ui.activity.login.LoginActivity
import br.com.brunamarcal.tmdbproject.ui.adapter.FavoriteAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite_movie.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers

class FavoriteMovieActivity : AppCompatActivity() {

    lateinit var favoriteAdapter: FavoriteAdapter
    lateinit var viewModel: FavoriteMovieViewModel
    lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movie)

        toolbarMain.title = getString(R.string.my_favorite)
        setSupportActionBar(toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        deleteMovie(favoriteRecyclerView)

        val repository = Repository(this)
        viewModel = FavoriteMovieViewModel.FavoriteMovieViewModelProviderFactory(application, repository, Dispatchers.IO).create(FavoriteMovieViewModel::class.java)

        val sharedPreference = SharedPreference(this)
        sharedPreference.getData(LoginActivity.USER)?.let {
            userEmail = it
        }

        viewModel.getFavoriteMovie(userEmail).observe(this, Observer { favoriteMovie ->
            favoriteMovie?.let {listFavoriteMovie ->
                setAdapter(listFavoriteMovie)
            }
        })

    }


    fun deleteMovie(view: View){
        val itemTouchHelperCallBack = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
              return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val favorite = favoriteAdapter.getCurrentMovie(viewHolder.adapterPosition)
                viewModel.deleteFavoriteMovie(favorite)
                showSnackBar(view, favorite)
            }

        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(favoriteRecyclerView)
        }

    }

    private fun setMovie(favorite: FavoriteMovie): MovieResult {
        return MovieResult(favorite.id, favorite.posterPath, favorite.overview, favorite.releaseDate, favorite.genreId, favorite.originalTitle, favorite.voteAverage)

    }

    private fun setAdapter(listMovie: List<FavoriteMovie>) {
        favoriteAdapter =  FavoriteAdapter(listMovie) { favoriteMovie ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("DATA_MOVIE", setMovie(favoriteMovie))
            startActivity(intent)
        }

        with(favoriteRecyclerView) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    fun showSnackBar(view: View, favorite: FavoriteMovie){
        Snackbar.make(view, "Filme deletado com Sucesso!", Snackbar.LENGTH_LONG).apply {
            setAction("Desfazer"){
                viewModel.insertFavoriteMovie(favorite)
            }
                show()
        }
    }

}





