package com.eathealthyapp.is3261.eathealthyapp.fragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.eathealthyapp.is3261.eathealthyapp.fragments.main_fragments.FragmentHome
import com.eathealthyapp.is3261.eathealthyapp.fragments.main_fragments.FragmentWallet


class FragmentPageAdaptor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val numPages = 2
    private val fragment_home: Fragment = FragmentHome()
    private val fragment_wallet: Fragment = FragmentWallet()

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment
        if (position == 0) {
            fragment = fragment_home
        } else {
            fragment = fragment_wallet
        }

        return fragment
    }

    override fun getCount(): Int {
        return numPages
    }
}
