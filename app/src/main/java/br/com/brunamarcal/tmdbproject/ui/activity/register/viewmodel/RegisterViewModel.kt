package br.com.brunamarcal.tmdbproject.ui.activity.register.viewmodel

import android.app.Application
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.brunamarcal.tmdbproject.data.database.modeldb.User
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application, private val repository: Repository, private val dispatcher: CoroutineDispatcher) : AndroidViewModel(application) {

    fun insertUser(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }

    fun registerIsValid(edtRegisterName: EditText, edtRegisterEmail: EditText, edtRegisterPassword: EditText): Int {
        return if (TextUtils.isEmpty(edtRegisterName.text.toString())) {
            return 0
        } else if (TextUtils.isEmpty(edtRegisterEmail.text.toString()) && TextUtils.isEmpty(edtRegisterEmail.text.toString())) {
            return 1
        } else if (TextUtils.isEmpty(edtRegisterPassword.text.toString())) {
            return 2
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtRegisterEmail.text.toString()).matches() || TextUtils.isEmpty(edtRegisterEmail.text.toString())) {
            return 3
        } else -1
    }

    class RegisterViewModelProviderFactory(private val application: Application, private val repository: Repository, private val dispatcher: CoroutineDispatcher) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                return RegisterViewModel(application, repository, dispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}