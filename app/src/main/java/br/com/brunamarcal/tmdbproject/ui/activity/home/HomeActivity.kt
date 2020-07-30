package br.com.brunamarcal.tmdbproject.ui.activity.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.pageadapter.HomePageAdapter
import br.com.brunamarcal.tmdbproject.ui.activity.favoritemovie.FavoriteMovieActivity
import br.com.brunamarcal.tmdbproject.ui.activity.home.viewmodel.HomeViewModel
import br.com.brunamarcal.tmdbproject.ui.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_base_home.*
import kotlinx.coroutines.Dispatchers

class HomeActivity : AppCompatActivity() {

    private val fragmentAdapter =
        HomePageAdapter(
            supportFragmentManager
        )
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
//            R.id.homeFragment -> {
//                val fragment = HomeFragment()
//                    supportFragmentManager.beginTransaction().replace(R.id.viewPagerMain, fragment.javaClass.simpleName).commit()
//            }
            R.id.favoriteFragment -> {
                startActivity(Intent(this@HomeActivity, FavoriteMovieActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
//            R.id.personFragment -> {}
            else -> false
        }
    }
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_home)

        val repository = Repository(this)
        viewModel = HomeViewModel.HomeViewModelProviderFactory(repository, Dispatchers.IO).create(HomeViewModel::class.java)

        viewPagerMain.adapter = fragmentAdapter
        tabsMain.setupWithViewPager(viewPagerMain)

        val bottomNavigation: BottomNavigationView = bottomNavigation
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}
