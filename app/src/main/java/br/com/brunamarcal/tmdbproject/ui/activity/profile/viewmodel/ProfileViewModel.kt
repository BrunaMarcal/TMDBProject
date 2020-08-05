package br.com.brunamarcal.tmdbproject.ui.activity.profile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.brunamarcal.tmdbproject.data.database.modeldb.User
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher

class ProfileViewModel(application: Application, private val repository: Repository, private val dispatcher: CoroutineDispatcher): AndroidViewModel(application) {

    fun getUser(email: String): LiveData<User> = repository.getUserByEmail(email)





    class ProfileViewModelProviderFactory(private val application: Application, private val repository: Repository, private val dispatcher: CoroutineDispatcher) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                return ProfileViewModel(application, repository, dispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }
}