package br.com.brunamarcal.tmdbproject.ui.activity.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.brunamarcal.tmdbproject.ClickListener
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.pageadapter.HomePageAdapter
import br.com.brunamarcal.tmdbproject.ui.activity.favoritemovie.FavoriteMovieActivity
import br.com.brunamarcal.tmdbproject.ui.activity.home.viewmodel.HomeViewModel
import br.com.brunamarcal.tmdbproject.ui.activity.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_base_home.*
import kotlinx.coroutines.Dispatchers

class HomeActivity : AppCompatActivity(), ClickListener {

    private val fragmentAdapter = HomePageAdapter(supportFragmentManager)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.favoriteFragment -> {
                startActivity(Intent(this@HomeActivity, FavoriteMovieActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.personFragment -> {
                startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            else -> false
        }
    }
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_home)

        val repository = Repository(this)
        viewModel = HomeViewModel.HomeViewModelProviderFactory(repository, Dispatchers.IO).create(HomeViewModel::class.java)

        ProfileActivity.setFinishClick(this)

        viewPagerMain.adapter = fragmentAdapter
        tabsMain.setupWithViewPager(viewPagerMain)

        val bottomNavigation: BottomNavigationView = bottomNavigation
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    override fun click() {
        finish()
    }
}
