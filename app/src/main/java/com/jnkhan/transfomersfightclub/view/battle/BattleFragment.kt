package com.jnkhan.transfomersfightclub.view.battle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jnkhan.transfomersfightclub.R
import com.jnkhan.transfomersfightclub.store.FighterResult
import com.jnkhan.transfomersfightclub.view.main.FighterAdapter
import com.jnkhan.transfomersfightclub.view.main.MainActivity

/**
 * A placeholder fragment containing a simple view.
 */
class BattleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_battle, container, false)

        val transformers = arguments?.getStringArrayList(KEY_FIGHTERS)
            ?: ArrayList<String>()

        val gson = Gson()

        val results = ArrayList<FighterResult>()
        for(transformer in transformers) {
            var transformer = gson.fromJson(transformer, FighterResult::class.java)
            if (transformer.survived)
                results.add(transformer)
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.list_results)

        val gridManager = GridLayoutManager(
            context,
            resources.getInteger(R.integer.results_columns),
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.layoutManager = gridManager

        if(context!=null) {
            val adapter = ResultsAdapter(requireContext(), results)
            recyclerView.adapter = adapter
        }

        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        var VALUE_WINNERS = 0
        var KEY_FIGHTERS = "fighters"

        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, transformers: ArrayList<String>): BattleFragment {
            return BattleFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putStringArrayList(KEY_FIGHTERS, transformers)
                }
            }
        }
    }
}