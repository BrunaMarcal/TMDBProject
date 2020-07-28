package br.com.brunamarcal.tmdbproject.ui.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.brunamarcal.tmdbproject.BuildConfig
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.model.MovieResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movie = intent.extras?.get("DATA_MOVIE") as MovieResult

        Picasso.get().load("""${BuildConfig.BASE_URL_IMAGE}${movie.posterPath}""").into(imageViewDetail)
        txtMovieDetailTitle.text = movie.originalTitle
        txtDetailDate.text = movie.releaseDate
        txtDetailVote.text = movie.voteAverage.toString()
        txtDetailOverview.text = movie.overview
    }
}
