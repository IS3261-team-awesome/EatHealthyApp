package com.eathealthyapp.is3261.eathealthyapp.fragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.eathealthyapp.is3261.eathealthyapp.fragments.FragmentDiary
import com.eathealthyapp.is3261.eathealthyapp.fragments.FragmentScanner
import com.eathealthyapp.is3261.eathealthyapp.fragments.FragmentWallet


class FragmentPageAdaptor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val numPages = 3
    private val fragment_diary: Fragment = FragmentDiary()
    private val fragment_wallet: Fragment = FragmentWallet()
    private val fragment_scanner: Fragment = FragmentScanner()

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment
        if (position == 0) {
            fragment = fragment_scanner
        } else if (position == 1) {
            fragment = fragment_diary
        } else {
            fragment = fragment_wallet
        }

        return fragment
    }

    override fun getCount(): Int {
        return numPages
    }

    /*override fun destroyItem(container: View, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)

        if (position != 0) {
            destroyItem()
        }
    }*/
}
