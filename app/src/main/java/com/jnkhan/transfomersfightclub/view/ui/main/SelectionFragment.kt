package com.jnkhan.transfomersfightclub.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jnkhan.transfomersfightclub.R
import com.jnkhan.transfomersfightclub.store.Transformer

/**
 * A placeholder fragment containing a simple view.
 */
class SelectionFragment : Fragment() {

    private lateinit var transformers : ArrayList<Transformer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        for( transformer in resources.getStringArray(R.array.stats_autobots)) {
        }

        val root = inflater.inflate(R.layout.fragment_selection, container, false)



        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): SelectionFragment {
            return SelectionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}