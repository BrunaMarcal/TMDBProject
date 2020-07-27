package br.com.brunamarcal.tmdbproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunamarcal.tmdbproject.BuildConfig
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.model.MovieResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(val movieList: List<MovieResult>) :
    RecyclerView.Adapter<MovieAdapter.AdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return AdapterViewHolder(itemView)
    }

    override fun getItemCount() = movieList.count()

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    class AdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtTitle = itemView.txtMovieTitle
        private val txtDate = itemView.txtMovieDate
        private val posterPath = itemView.imagePosterPath
        private val txtVote = itemView.txtVote
        private val picasso = Picasso.get()

        fun bind(movie: MovieResult) {
            txtTitle.text = movie.originalTitle
            txtDate.text = movie.releaseDate
            txtVote.text = movie.voteAverage.toString()

            movie.posterPath.let {
                picasso.load("""${BuildConfig.BASE_URL_IMAGE}${movie.posterPath}""")
                    .into(posterPath)
            }

        }

    }

}