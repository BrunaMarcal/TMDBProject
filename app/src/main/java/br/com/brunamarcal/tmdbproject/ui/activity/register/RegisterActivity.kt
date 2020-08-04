package br.com.brunamarcal.tmdbproject.ui.activity.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.database.modeldb.User
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.ui.activity.favoritemovie.viewmodel.FavoriteMovieViewModel
import br.com.brunamarcal.tmdbproject.ui.activity.login.LoginActivity
import br.com.brunamarcal.tmdbproject.ui.activity.register.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_person.*
import kotlinx.coroutines.Dispatchers
import java.util.Observer

class RegisterActivity : AppCompatActivity() {

    lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val repository = Repository(this)
        viewModel = RegisterViewModel.RegisterViewModelProviderFactory(application, repository, Dispatchers.IO).create(RegisterViewModel::class.java)

        register()

    }


    fun register() {
        btnRegister.setOnClickListener {
            when (viewModel.registerIsValid(edtRegisterName, edtRegisterEmail, edtRegisterPassword)) {
                0 -> Toast.makeText(this@RegisterActivity, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                1 -> Toast.makeText(this@RegisterActivity, "Fill in all fields", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(this@RegisterActivity, "Fill in the password", Toast.LENGTH_SHORT).show()
                3 -> Toast.makeText(this@RegisterActivity, "Invalid Email", Toast.LENGTH_SHORT).show()
                else -> {
                    viewModel.insertUser(User(edtRegisterEmail.text.toString(), edtRegisterName.text.toString(), edtRegisterPassword.text.toString()))
                }
        }
            Toast.makeText(this@RegisterActivity, "Registration successfully Complete", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
    }
}
}



