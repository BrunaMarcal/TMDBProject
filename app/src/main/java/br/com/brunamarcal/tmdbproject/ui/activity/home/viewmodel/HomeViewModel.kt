package br.com.brunamarcal.tmdbproject.ui.activity.home.viewmodel


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
import retrofit2.Response

class HomeViewModel(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModel(){
   val movieResponse = MutableLiveData<State<MovieResponse>>()

  fun getMovie(apiKey: String, language: String, includeAdult: Boolean) = viewModelScope.launch {
    try {
        movieResponse.postValue(State.loading(true))
        val response = withContext(ioDispatcher) {
            repository.getMovies(apiKey, language, includeAdult)
        }
        movieResponse.postValue(State.success(response))
    }catch (throwable: Throwable){
        movieResponse.postValue(State.error(throwable))
    }
}
    class HomeViewModelProviderFactory(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
                return HomeViewModel(repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}