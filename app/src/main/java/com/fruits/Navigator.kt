package com.fruits

import android.support.v4.app.FragmentManager
import com.fruits.detail.DetailFragment
import com.fruits.fruits.FruitListFragment

class Navigator(mainActivity: MainActivity, containerId: Int) {

    private var fragmentManager: FragmentManager = mainActivity.getSupportFragmentManager()
    private var containerId = containerId

    fun toListFruitFragment() {
        var fruitListFragment = FruitListFragment()

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

