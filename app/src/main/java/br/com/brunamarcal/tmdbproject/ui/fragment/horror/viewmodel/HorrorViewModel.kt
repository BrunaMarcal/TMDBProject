package br.com.brunamarcal.tmdbproject.ui.fragment.horror.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.brunamarcal.tmdbproject.core.State
import br.com.brunamarcal.tmdbproject.data.model.MovieResponse
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HorrorViewModel(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModel() {
    val responseHorror = MutableLiveData<State<MovieResponse>>()

    fun getMovieGenreHorror(apiKey: String, withGenres: Int, language: String) = viewModelScope.launch {
        try {
            responseHorror.value = State.loading(true)
            val response = withContext(ioDispatcher){
                repository.getMovieGenres(apiKey, withGenres, language)
            }
            responseHorror.postValue(State.success(response))
        } catch (t: Throwable){
            responseHorror.postValue(State.error(t))
        }
    }

    class HorrorViewModelProviderFactory(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HorrorViewModel::class.java)) {
                return HorrorViewModel(repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}