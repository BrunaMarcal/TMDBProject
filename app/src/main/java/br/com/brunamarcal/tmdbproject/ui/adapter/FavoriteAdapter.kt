package br.com.brunamarcal.tmdbproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunamarcal.tmdbproject.BuildConfig
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.database.modeldb.FavoriteMovie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

class FavoriteAdapter(val favoriteMovie: List<FavoriteMovie>): RecyclerView.Adapter<FavoriteAdapter.AdapterFavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.AdapterFavoriteViewHolder {
    val itemFavoriteView =  LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return FavoriteAdapter.AdapterFavoriteViewHolder(itemFavoriteView)

    }

    override fun getItemCount() = favoriteMovie.count()

    override fun onBindViewHolder(holder: FavoriteAdapter.AdapterFavoriteViewHolder, position: Int) {
        holder.bind(favoriteMovie[position])
    }

    class AdapterFavoriteViewHolder(itemFavoriteView: View): RecyclerView.ViewHolder(itemFavoriteView) {

        private val txtTitle = itemView.txtMovieTitle
        private val txtDate = itemView.txtMovieDate
        private val posterPath = itemView.imagePosterPath
        private val txtVote = itemView.txtVote
        private val picasso = Picasso.get()

        fun bind(favoriteMovie: FavoriteMovie ) {
            txtTitle.text = favoriteMovie.originalTitle
            txtDate.text = favoriteMovie.releaseDate
            txtVote.text = favoriteMovie.voteAverage.toString()

            favoriteMovie.posterPath.let {
                picasso.load("""${BuildConfig.BASE_URL_IMAGE}${favoriteMovie.posterPath}""")
                    .into(posterPath)
            }
        }
    }
}