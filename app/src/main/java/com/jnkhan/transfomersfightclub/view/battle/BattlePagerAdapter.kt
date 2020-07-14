package com.jnkhan.transfomersfightclub.view.battle

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.gson.Gson
import com.jnkhan.transfomersfightclub.R
import com.jnkhan.transfomersfightclub.store.FighterResult

private val TAB_TITLES = arrayOf(
    R.string.battle_tab_text_1,
    R.string.battle_tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class BattlePagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var winners = ArrayList<FighterResult>()
    private var survivers = ArrayList<FighterResult>()

    fun setWinners(transformers: ArrayList<FighterResult>) {
        winners = transformers
    }

    fun setsurvivers(transformers: ArrayList<FighterResult>) {
        survivers = transformers
    }

    companion object {
        val VALUE_WINNERS = "Winners"
        val VALUE_SURVIVORS = "Survivors"
    }

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        val results = ArrayList<String>()
        val gson = Gson()

        if(position==BattleFragment.VALUE_WINNERS) {

            for(transformer in winners)
                results.add(gson.toJson(transformer))

            return BattleFragment.newInstance(position, results)
        }

        for(transformer in survivers)
            results.add(gson.toJson(transformer))

        return BattleFragment.newInstance(position, results)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}