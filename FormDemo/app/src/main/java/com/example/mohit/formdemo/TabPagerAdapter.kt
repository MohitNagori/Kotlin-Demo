package com.example.mohit.formdemo

/**
 * Created by Mohit on 4/18/2018.
 */

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TabPagerAdapter(fm: FragmentManager, private var tabCount: Int, var stuList : ArrayList<Student>) :
        FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return ListFragment.newInstance(stuList)
            1 -> return AddFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}