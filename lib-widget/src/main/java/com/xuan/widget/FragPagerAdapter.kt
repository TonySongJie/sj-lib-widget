package com.xuan.widget

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup

/**
 * @packageName com.xuan.common.adapter
 * @fileName MyFragmentPagerAdapter
 * @date 2018/12/13 0013 14:11
 * @author 宋杰
 * @describe TODO
 */
class FragPagerAdapter(fm: FragmentManager, private val fragments: List<Fragment>) :
    FragmentPagerAdapter(fm) {

    private val mFragManager: FragmentManager? = fm

    override fun getItemPosition(any: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getItem(position: Int): Fragment {
        val fragment = fragments[position]
        val bundle = Bundle()
        bundle.putString("id", "" + position)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return if (fragments.isNotEmpty()) fragments.size else 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        mFragManager!!.beginTransaction().show(fragment).commit()
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = fragments[position]
        mFragManager!!.beginTransaction().hide(fragment).commit()
    }
}