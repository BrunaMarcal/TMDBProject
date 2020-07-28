package br.com.brunamarcal.tmdbproject.ui.activity.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.pageadapter.HomePageAdapter
import br.com.brunamarcal.tmdbproject.ui.activity.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_base_home.*
import kotlinx.coroutines.Dispatchers

class HomeActivity : AppCompatActivity() {

    private val fragmentAdapter =
        HomePageAdapter(
            supportFragmentManager
        )
//    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId){
//            R.id.homeFragment -> {
//                val fragment = HomeFragment()
//                    supportFragmentManager.beginTransaction().replace(R.id.viewPagerMain, fragment.javaClass.simpleName).commit()
//            }
//            R.id.favoriteFragment -> {}
//            R.id.personFragment -> {}
//            else -> false
//        }
//    }
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_home)

        val repository = Repository()
        viewModel = HomeViewModel.HomeViewModelProviderFactory(repository, Dispatchers.IO).create(HomeViewModel::class.java)

        viewPagerMain.adapter = fragmentAdapter
        tabsMain.setupWithViewPager(viewPagerMain)

    }
}
