package br.com.brunamarcal.tmdbproject.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import br.com.brunamarcal.tmdbproject.data.database.TmdbDatabase
import br.com.brunamarcal.tmdbproject.data.database.modeldb.FavoriteMovie
import br.com.brunamarcal.tmdbproject.data.database.modeldb.FavoriteMovieDao
import br.com.brunamarcal.tmdbproject.data.database.modeldb.User
import br.com.brunamarcal.tmdbproject.data.database.modeldb.UserDao
import br.com.brunamarcal.tmdbproject.data.network.ApiService

class Repository(context: Context) {

    private val favoriteDao: FavoriteMovieDao by lazy {
        TmdbDatabase.getDb(context).favoriteMovieDao()
    }
    private val userDao: UserDao by lazy {
        TmdbDatabase.getDb(context).userDao()
    }

    suspend fun insertUser(user: User){
        userDao.upsertUser(user)
    }

//    fun getUser(email: String, password: String): LiveData<User>{
//       return userDao.selectedUser(email, password)
//    }

    fun getUser (email: String, password: String): LiveData<User> = userDao.selectedUser(email, password)

    suspend fun deleteUser(user:User){
        userDao.deleteUser(user)
    }

    fun getUserByEmail(email: String): LiveData <User> = userDao.selectedEmail(email)


    suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovie){
        favoriteDao.insertMovie(favoriteMovie)
    }

    fun getFavoriteMovie(userEmail: String): LiveData<List<FavoriteMovie>> = favoriteDao.selectFavoriteMovie(userEmail)


    suspend fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie){
        favoriteDao.deleteFavoriteMovie(favoriteMovie)
    }



    suspend fun getMovies(apiKey: String, language: String, includeAdult: Boolean) =
        ApiService.service.getMovie(apiKey, language, includeAdult)
}