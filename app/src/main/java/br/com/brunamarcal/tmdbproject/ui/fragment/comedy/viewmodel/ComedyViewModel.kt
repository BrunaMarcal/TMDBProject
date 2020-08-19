package br.com.brunamarcal.tmdbproject.ui.fragment.comedy.viewmodel

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

class ComedyViewModel(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModel() {
    val responseComedy = MutableLiveData<State<MovieResponse>>()

    fun getMovieGenreComedy(apiKey: String, withGenres: Int, language: String) =
        viewModelScope.launch {
            try {
                responseComedy.value = State.loading(true)
                val response = withContext(ioDispatcher) {
                    repository.getMovieGenres(apiKey, withGenres, language)
                }
                responseComedy.postValue(State.success(response))
            } catch (t: Throwable) {
                responseComedy.postValue(State.error(t))
            }
        }

    class ComedyViewModelProviderFactory(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ComedyViewModel::class.java)) {
                return ComedyViewModel(repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}