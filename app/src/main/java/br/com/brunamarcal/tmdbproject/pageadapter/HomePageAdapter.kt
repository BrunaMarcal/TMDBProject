package br.com.brunamarcal.tmdbproject.pageadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.brunamarcal.tmdbproject.ui.fragment.FavoriteFragment
import br.com.brunamarcal.tmdbproject.ui.fragment.HomeFragment
import br.com.brunamarcal.tmdbproject.ui.fragment.PersonFragment

class HomePageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> HomeFragment()
            1 -> FavoriteFragment()
            2 -> PersonFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "Home"
            1 -> "Favorites"
            2 -> "Person"
            else -> null
        }
    }
}