package com.bobabelga.moviesapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bobabelga.moviesapp.ui.fragments.FavoriteMoviesFragment
import com.bobabelga.moviesapp.ui.fragments.PopularMoviesFragment
import com.bobabelga.moviesapp.ui.fragments.SearchMoviesFragment

class ViewPagerAdapter : FragmentStateAdapter{

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)
    constructor(fragment: Fragment) : super(fragment)
    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) : super(
        fragmentManager,
        lifecycle
    )


    override fun getItemCount(): Int  = 3

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return PopularMoviesFragment()
            1 -> return FavoriteMoviesFragment()
            2 -> return SearchMoviesFragment()
        }
        return PopularMoviesFragment()
    }

}