package br.com.brunamarcal.tmdbproject.pageadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.brunamarcal.tmdbproject.ui.fragment.action.ActionFragment
import br.com.brunamarcal.tmdbproject.ui.fragment.horror.HorrorFragment
import br.com.brunamarcal.tmdbproject.ui.fragment.pop.PopFragment
import br.com.brunamarcal.tmdbproject.ui.fragment.comedy.ComedyFragment

class HomePageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> PopFragment()
            1 -> HorrorFragment()
            2 -> ComedyFragment()
            3 -> ActionFragment()
            else -> PopFragment()
        }
    }

    override fun getCount(): Int {
        return 4
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