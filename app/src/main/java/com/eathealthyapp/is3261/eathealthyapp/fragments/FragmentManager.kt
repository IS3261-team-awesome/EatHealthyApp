package com.eathealthyapp.is3261.eathealthyapp.fragments

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.eathealthyapp.is3261.eathealthyapp.MainActivity
import com.eathealthyapp.is3261.eathealthyapp.R

class FragmentManager(val activity: MainActivity) {
    lateinit var viewPager: ViewPager


    fun setUp() {
        // Set viewpager
        val fragmentPageAdaptor = FragmentPageAdaptor(activity.supportFragmentManager)
        viewPager = activity.findViewById<ViewPager>(R.id.myvp)
        viewPager.adapter = fragmentPageAdaptor
        viewPager.offscreenPageLimit = 3
        viewPager.currentItem = 1

        // To make camera and detection start only when it is on camera fragment
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                val scannerFragment: FragmentScanner = fragmentPageAdaptor.getItem(0) as FragmentScanner
                if (position == 0) {
                    scannerFragment.startDetection()
                } else {
                    scannerFragment.stopDetection()
                }
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })

        // Setup Btm Tab
        val btmTabLayout = activity.findViewById<TabLayout>(R.id.btmViewPagerTabLayout)
        btmTabLayout.setupWithViewPager(viewPager)
        btmTabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_camera)
        btmTabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_diary)
        btmTabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_wallet)
    }

    fun setSelectedFragment(fragmentNumber: Int) {
        viewPager.currentItem = fragmentNumber
    }
}
