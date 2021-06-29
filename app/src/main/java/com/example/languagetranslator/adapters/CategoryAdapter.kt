package com.example.languagetranslator.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.languagetranslator.fragments.ColorsFragment
import com.example.languagetranslator.fragments.FamilyFragment
import com.example.languagetranslator.fragments.NumbersFragment
import com.example.languagetranslator.fragments.PhrasesFragment
import com.example.languagetranslator.R

class CategoryAdapter(private val mContext: Context, fm: FragmentManager?): FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            NumbersFragment()
        } else if (position == 1) {
            FamilyFragment()
        } else if (position == 2) {
            ColorsFragment()
        } else {
            PhrasesFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) {
            mContext.getString(R.string.category_numbers)
        } else if (position == 1) {
            mContext.getString(R.string.category_family)
        } else if (position == 2) {
            mContext.getString(R.string.category_colors)
        } else {
            mContext.getString(R.string.category_phrases)
        }
    }
}