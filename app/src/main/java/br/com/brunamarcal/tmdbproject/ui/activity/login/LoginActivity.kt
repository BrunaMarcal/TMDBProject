package br.com.brunamarcal.tmdbproject.ui.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.data.util.SharedPreference
import br.com.brunamarcal.tmdbproject.ui.activity.home.HomeActivity
import br.com.brunamarcal.tmdbproject.ui.activity.login.viewmodel.LoginViewModel
import br.com.brunamarcal.tmdbproject.ui.activity.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers

class LoginActivity : AppCompatActivity() {

    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val repository = Repository(this)
        viewModel = LoginViewModel.LoginViewModelProviderFactory(application, repository, Dispatchers.IO).create(LoginViewModel::class.java)

        val sharedPreference = SharedPreference(this)

        login(sharedPreference)

        txtRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()

        }

    }

    fun login(sharedPreference: SharedPreference){
        btnAccess.setOnClickListener {
            when(viewModel.isValid(edtEmailLogin, edtPasswordLogin)){
                0 -> Toast.makeText(this@LoginActivity, "Fill in all fields", Toast.LENGTH_SHORT).show()
                1 -> Toast.makeText(this@LoginActivity, "Fill in the password", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(this@LoginActivity, "Invalid Email", Toast.LENGTH_SHORT).show()
                else -> {
                    viewModel.getUser(edtEmailLogin.text.toString(), edtPasswordLogin.text.toString()).observe(this, Observer {
                        it?.let {
                            sharedPreference.saveData(USER, it.email)
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        } ?: kotlin.run {
                            Toast.makeText(this, "E-mail or Password invalid", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }

    companion object {
        const val USER = "USER"
    }


}
