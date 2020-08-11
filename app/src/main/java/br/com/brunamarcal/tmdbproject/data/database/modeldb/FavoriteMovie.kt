package br.com.brunamarcal.tmdbproject.data.database.modeldb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite_movie")
@Parcelize
data class FavoriteMovie(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: Long,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "genre_id")
    val genreId: List<Int>,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double

): Parcelable