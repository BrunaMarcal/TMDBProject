package br.com.brunamarcal.tmdbproject.ui.activity.login.viewmodel

import android.app.Application
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.brunamarcal.tmdbproject.data.database.modeldb.User
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import java.lang.IllegalArgumentException

class LoginViewModel (application: Application, private val repository: Repository, private val dispatcher: CoroutineDispatcher): AndroidViewModel(application) {

    fun getUser(email: String, password: String): LiveData<User> =
        repository.getUser(email, password)

    fun isValid(edtEmail: EditText, edtPassword: EditText): Int{
        return if (TextUtils.isEmpty(edtEmail.text.toString()) && TextUtils.isEmpty(edtPassword.text.toString())) {
            return  0
        } else if (TextUtils.isEmpty(edtPassword.text.toString())) {
            return  1
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.text.toString()).matches() || TextUtils.isEmpty(edtEmail.text.toString())) {
            return 2
        } else -1
    }

class LoginViewModelProviderFactory(private val application: Application, private val repository: Repository, private val dispatcher: CoroutineDispatcher): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(application, repository, dispatcher) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}


}