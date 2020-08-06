package br.com.brunamarcal.tmdbproject.ui.activity.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.database.modeldb.User
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.data.util.SharedPreference
import br.com.brunamarcal.tmdbproject.ui.activity.login.LoginActivity
import br.com.brunamarcal.tmdbproject.ui.activity.profile.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers

class ProfileActivity : AppCompatActivity() {

    lateinit var viewModel: ProfileViewModel
    lateinit var getUserEmail: String
    lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        toolbarMain.title = ""
        setSupportActionBar(toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        logout.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
        }

        imgDeleteUser.setOnClickListener {
            viewModel.deleteUser(mUser)
        }

            val repository = Repository(this)
            viewModel = ProfileViewModel.ProfileViewModelProviderFactory(
                application,
                repository,
                Dispatchers.IO
            ).create(ProfileViewModel::class.java)

            val sharedPreference = SharedPreference(this)
            sharedPreference.getData(LoginActivity.USER)?.let {
                getUserEmail = it
            }

            viewModel.getUser(getUserEmail).observe(this, Observer {
                it?.let { user ->
                    mUser = user
                    txtUserNameProfile.text = user.name
                    txtUserEmailProfile.text = user.email
                    txtuserPasswordProfile.text = user.password
                }
            })

        }
    }

