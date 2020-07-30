package br.com.brunamarcal.tmdbproject.ui.activity.favoritemovie.viewmodel

import android.app.Application
import androidx.lifecycle.*
import br.com.brunamarcal.tmdbproject.data.database.modeldb.FavoriteMovie
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class FavoriteMovieViewModel(application: Application, private val repository: Repository, private val dispatcher: CoroutineDispatcher) : AndroidViewModel(application) {

    fun getFavoriteMovie(userEmail: String): LiveData<List<FavoriteMovie>> = repository.getFavoriteMovie(userEmail)


    fun insertFavoriteMovie(favoriteMovie: FavoriteMovie) = viewModelScope.launch {
        repository.insertFavoriteMovie(favoriteMovie)
    }

    fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie) = viewModelScope.launch {
        repository.deleteFavoriteMovie(favoriteMovie)

    }

    class FavoriteMovieViewModelProviderFactory(val application: Application, private val repository: Repository, private val ioDispatcher: CoroutineDispatcher) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java)) {
                return FavoriteMovieViewModel(application, repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}