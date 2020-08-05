package br.com.brunamarcal.tmdbproject.ui.activity.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.ui.activity.profile.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers

class ProfileActivity : AppCompatActivity() {

    lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        toolbarMain.title=""
        setSupportActionBar(toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        val repository = Repository(this)
        viewModel = ProfileViewModel.ProfileViewModelProviderFactory(application, repository, Dispatchers.IO).create(ProfileViewModel::class.java)

        viewModel.getUser("bruna@zup.com").observe( this, Observer {
            it?.let { user ->
                txtUserNameProfile.text = user.name
                txtUserEmailProfile.text = user.email
                txtuserPasswordProfile.text = user.password
            }
        })

    }
}
