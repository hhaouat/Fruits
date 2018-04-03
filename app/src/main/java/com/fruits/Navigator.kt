package com.fruits

import android.support.v4.app.FragmentManager
import com.fruits.detail.DetailFragment
import com.fruits.fruits.FruitListFragment

class Navigator(mainActivity: MainActivity, private val containerId: Int) {

    private val fragmentManager: FragmentManager = mainActivity.getSupportFragmentManager()

    fun toListFruitFragment() {
        val fruitListFragment = FruitListFragment()

        fragmentManager.beginTransaction()
                .replace(containerId, fruitListFragment)
                .commitAllowingStateLoss()
    }

    fun toDetailFruitFragment() {
        val detailFragment = DetailFragment()

        fragmentManager.beginTransaction()
                .replace(containerId, detailFragment)
                .commitAllowingStateLoss()
    }
}

