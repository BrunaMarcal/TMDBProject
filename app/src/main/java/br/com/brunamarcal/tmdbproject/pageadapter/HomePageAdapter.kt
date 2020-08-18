package br.com.brunamarcal.tmdbproject.pageadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.brunamarcal.tmdbproject.ui.fragment.HorrorFragment
import br.com.brunamarcal.tmdbproject.ui.fragment.PopFragment
import br.com.brunamarcal.tmdbproject.ui.fragment.ComedyFragment

class HomePageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> PopFragment()
            1 -> HorrorFragment()
            2 -> ComedyFragment()
            else -> PopFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "+Pop"
            1 -> "Horror"
            2 -> "Comedy"
            3 -> "Action"
            else -> null
        }
    }
}