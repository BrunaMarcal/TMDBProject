package br.com.brunamarcal.tmdbproject.ui.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.brunamarcal.tmdbproject.BuildConfig
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.database.modeldb.FavoriteMovie
import br.com.brunamarcal.tmdbproject.data.model.MovieResult
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.ui.activity.favoritemovie.viewmodel.FavoriteMovieViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers

class DetailActivity : AppCompatActivity() {
     lateinit var viewModel: FavoriteMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val repository = Repository(this)
        viewModel = FavoriteMovieViewModel.FavoriteMovieViewModelProviderFactory(application, repository, Dispatchers.IO).create(FavoriteMovieViewModel::class.java)

        val movie = intent.extras?.get("DATA_MOVIE") as MovieResult

        Picasso.get().load("""${BuildConfig.BASE_URL_IMAGE}${movie.posterPath}""").into(imageViewDetail)
        txtMovieDetailTitle.text = movie.originalTitle
        txtDetailDate.text = movie.releaseDate
        txtDetailVote.text = movie.voteAverage.toString()
        txtDetailOverview.text = movie.overview

        fabDetail.setOnClickListener {
            viewModel.insertFavoriteMovie(FavoriteMovie(movie.id,
                "bruna@zup.com",
                movie.posterPath,
                movie.overview,
                movie.releaseDate,
                movie.genreId,
                movie.originalTitle,
                movie.voteAverage))

            Snackbar.make(fabDetail, "Favoritado com Sucesso!", Snackbar.LENGTH_SHORT).show()
        }
    }
}
