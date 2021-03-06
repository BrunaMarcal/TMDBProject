package br.com.brunamarcal.tmdbproject.data.database.modeldb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(favoriteMovie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movie WHERE user_id = :userId")
    fun selectFavoriteMovie (userId: Long): LiveData<List<FavoriteMovie>>

    @Delete
    suspend fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie)
}