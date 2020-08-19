package br.com.brunamarcal.tmdbproject.ui.fragment.action.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.brunamarcal.tmdbproject.core.State
import br.com.brunamarcal.tmdbproject.data.model.MovieResponse
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.ui.activity.home.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActionViewModel(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModel() {
    val responseAction = MutableLiveData<State<MovieResponse>>()

    fun getMoviesGenres(apiKey: String, withGenres: Int, language: String) = viewModelScope.launch {
        try {
            responseAction.value = State.loading(true)
            val response = withContext(ioDispatcher){
                repository.getMovieGenres(apiKey, withGenres, language)
            }
            responseAction.postValue(State.success(response))
        } catch (t: Throwable){
            responseAction.postValue(State.error(t))
        }
    }

    class ActionViewModelProviderFactory(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ActionViewModel::class.java)) {
                return ActionViewModel(repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}