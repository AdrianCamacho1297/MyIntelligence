package com.example.myintelligence

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                return FragmentTest()
            }
            1 -> {
                return FragmentGraph()
            }
            else -> {
                return FragmentUser()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "Pruebas"
            }
            1 -> {
                "Estadisticas"
            }
            else -> {
                return "Datos"
            }
        }
    }
}