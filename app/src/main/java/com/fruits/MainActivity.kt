package com.fruits

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager


class MainActivity : AppCompatActivity() {

    private var containerId: Int = 1
    private var fragmentManager: FragmentManager = getSupportFragmentManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fruitListFragment = FruitListFragment()

        containerId = R.id.container;

        fragmentManager.beginTransaction()
                .replace(containerId, fruitListFragment)
                .commitAllowingStateLoss()
    }
}
