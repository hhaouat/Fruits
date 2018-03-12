package com.fruits

import android.support.v4.app.FragmentManager

class Navigator(mainActivity: MainActivity) {

    private var fragmentManager: FragmentManager = mainActivity.getSupportFragmentManager()

    fun toListFruitFragment(containerId: Int) {
        var fruitListFragment = FruitListFragment()

        fragmentManager.beginTransaction()
                .replace(containerId, fruitListFragment)
                .commitAllowingStateLoss()
    }
}