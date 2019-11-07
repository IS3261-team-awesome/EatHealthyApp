package com.eathealthyapp.is3261.eathealthyapp.fragments

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.ViewGroup
import com.eathealthyapp.is3261.eathealthyapp.activities.MainActivity
import com.eathealthyapp.is3261.eathealthyapp.R
import com.eathealthyapp.is3261.eathealthyapp.activities.ActivityScanner

class FragmentManager(val activity: MainActivity) {
    lateinit var viewPager: ViewPager


    fun setup() {
        // Setup viewpager
        val fragmentPageAdaptor = FragmentPageAdaptor(activity.supportFragmentManager)
        viewPager = activity.findViewById<ViewPager>(R.id.myvp)
        viewPager.adapter = fragmentPageAdaptor
        viewPager.offscreenPageLimit = 3
        viewPager.currentItem = 0

        // Setup Btm Tab
        val btmTabLayout = activity.findViewById<TabLayout>(R.id.btmViewPagerTabLayout)
        btmTabLayout.setupWithViewPager(viewPager)
        btmTabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_home_selected)
        btmTabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_wallet)
        btmTabLayout.setSelectedTabIndicatorHeight(0)

        val tab0 = (btmTabLayout.getChildAt(0) as ViewGroup).getChildAt(0)
        val p0 = tab0.getLayoutParams() as ViewGroup.MarginLayoutParams
        p0.setMargins(0, 0, 200, 0)

        val tab1 = (btmTabLayout.getChildAt(0) as ViewGroup).getChildAt(1)
        val p2 = tab1.getLayoutParams() as ViewGroup.MarginLayoutParams
        p2.setMargins(200, 0, 0, 0)

        // Add listener to change colour of icon when fragment is selected
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    btmTabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_home_selected)
                    btmTabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_wallet)
                } else if (position == 1) {
                    btmTabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_home)
                    btmTabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_wallet_selected)
                }
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    fun setSelectedFragment(fragmentNumber: Int) {
        viewPager.currentItem = fragmentNumber
    }
}
