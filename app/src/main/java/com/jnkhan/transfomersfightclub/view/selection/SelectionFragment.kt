package com.jnkhan.transfomersfightclub.view.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jnkhan.transfomersfightclub.R
import com.jnkhan.transfomersfightclub.store.Transformer
import com.jnkhan.transfomersfightclub.viewmodel.TfcViewModel
import com.jnkhan.transfomersfightclub.viewmodel.TfcViewModelFactory

/**
 * A placeholder fragment containing a simple view.
 */
class SelectionFragment : Fragment() {

    private var transformers = ArrayList<Transformer>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_selection, container, false)

        val team = arguments?.get(TEAM)

        var arrayResourceId = R.array.stats_autobots
        var teamSymbol = TfcViewModel.AUTOBOT_CHARACTER
        if (SelectionPagerAdapter.VALUE_AUTOBOTS != team) {
            arrayResourceId = R.array.stats_decepticons
            teamSymbol = TfcViewModel.DECEPTICON_CHARACTER
        }

        val gson = Gson();
        for (transformer in resources.getStringArray(arrayResourceId)) {
            transformers.add(
                gson.fromJson(transformer, Transformer::class.java)
                    .apply { this.team = teamSymbol })
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.selection_grid)

        val gridManager = GridLayoutManager(this.activity, 1, LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = gridManager

        if (context != null)
            recyclerView.adapter =
                SelectionAdapter(context!!.applicationContext, transformers)
                { selection -> onClick(selection) }

        return root
    }

    fun onClick(transformer: Transformer) {
        val viewModel = ViewModelProvider(this, TfcViewModelFactory(this.activity!!.application)).get(TfcViewModel::class.java)
        viewModel.addATransformer(transformer)

        activity?.finish()
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val TEAM = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): SelectionFragment {
            return SelectionFragment()
                .apply {
                    arguments = Bundle().apply {
                        putInt(TEAM, sectionNumber)
                    }
                }
        }
    }
}