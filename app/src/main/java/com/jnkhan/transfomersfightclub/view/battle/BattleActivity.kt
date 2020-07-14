package com.jnkhan.transfomersfightclub.view.battle

import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.jnkhan.transfomersfightclub.R
import com.jnkhan.transfomersfightclub.store.FighterResult
import com.jnkhan.transfomersfightclub.store.Transformer
import com.jnkhan.transfomersfightclub.view.main.MainActivity
import com.jnkhan.transfomersfightclub.view.selection.SelectionFragment
import com.jnkhan.transfomersfightclub.viewmodel.TfcViewModel
import java.lang.Math.abs
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class BattleActivity : AppCompatActivity() {

    val decepticonsResults = ArrayList<FighterResult>()
    val autobotResults = ArrayList<FighterResult>()

    var autoWins = 0
    var decWins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)
        val battlePagerAdapter =
            BattlePagerAdapter(
                this,
                supportFragmentManager
            )

        /**
         * Convert transformer strings to objects
         */
        val transformers = intent.getStringArrayListExtra(MainActivity.KEY_TRANSFORMER_BATTLE)
        val gson = Gson()
        val decepticons = TreeSet<Transformer>(Comparator() { a, b -> b.rating - a.rating })
        val autobots = TreeSet<Transformer>(Comparator() { a, b -> b.rating - a.rating })

        for (transformer in transformers) {
            var bot = gson.fromJson(transformer, Transformer::class.java)
            if (bot.team == TfcViewModel.AUTOBOT_CHARACTER) autobots.add(bot)
            else decepticons.add(bot)
        }
        Log.e("TEST", decepticons.toString())
        Log.e("TEST", autobots.toString())


        /**
         * Do battle
         */
        var specialCase = false

        while (!autobots.isEmpty() || !decepticons.isEmpty()) {

            if (!autobots.isEmpty() && !decepticons.isEmpty()) {
                var auto = autobots.pollFirst()
                var dec = decepticons.pollFirst()

                if (auto.name.toLowerCase() == "optimus prime" && dec.name.toLowerCase() == "predaking") {
                    specialCase = true
                    break

                } else if (auto.name.toLowerCase() == "optimus prime") {
                    autoWins++
                    autobotResults.add(convertFighter(auto, true))
                    decepticonsResults.add((convertFighter(dec, false)))

                } else if (dec.name.toLowerCase() == "predaking") {
                    decWins++
                    decepticonsResults.add((convertFighter(dec, true)))
                    autobotResults.add(convertFighter(auto, false))

                } else {
                    if (auto.courage - dec.courage >= 4 && auto.strength - dec.strength >= 3) {
                        autoWins++
                        autobotResults.add(convertFighter(auto, true))
                        decepticonsResults.add((convertFighter(dec, true)))

                    } else if (dec.courage - auto.courage >= 4 && dec.strength - auto.strength >= 3) {
                        decWins++
                        autobotResults.add(convertFighter(auto, true))
                        decepticonsResults.add((convertFighter(dec, true)))

                    } else if (auto.skill - dec.skill >= 3) {
                        autoWins++
                        autobotResults.add(convertFighter(auto, true))
                        decepticonsResults.add((convertFighter(dec, false)))

                    } else if (dec.skill - auto.skill >= 3) {
                        decWins++
                        decepticonsResults.add((convertFighter(dec, true)))
                        autobotResults.add(convertFighter(auto, false))

                    } else if (auto.rating > dec.rating) {
                        autoWins++
                        autobotResults.add(convertFighter(auto, true))
                        decepticonsResults.add((convertFighter(dec, false)))

                    } else if (dec.rating > auto.rating) {
                        decWins++
                        decepticonsResults.add((convertFighter(dec, true)))
                        autobotResults.add(convertFighter(auto, false))

                    } else {
                        decepticonsResults.add((convertFighter(dec, false)))
                        autobotResults.add(convertFighter(auto, false))
                    }
                }

            } else {
                while (!autobots.isEmpty())
                    autobotResults.add(convertFighter(autobots.pollFirst(), true))
                while (!decepticons.isEmpty())
                    decepticonsResults.add(convertFighter(decepticons.pollFirst(), true))
            }
        }

        /**
         * Counting the points
         */
        if(specialCase) {
            battlePagerAdapter.setWinners(ArrayList<FighterResult>())
            battlePagerAdapter.setsurvivers(ArrayList<FighterResult>())
        }
        else if (autoWins > decWins) {
            battlePagerAdapter.setWinners(autobotResults)
            battlePagerAdapter.setsurvivers(decepticonsResults)
        } else if (decWins > autoWins) {
            battlePagerAdapter.setsurvivers(autobotResults)
            battlePagerAdapter.setWinners(decepticonsResults)
        } else {
            var ratingsA = 0
            var ratingsD = 0
            var survivorsA = 0
            var survivorsD = 0

            for(transformer in autobotResults) {
                ratingsA += transformer.rating
                if(transformer.survived)
                    survivorsA++
            }
            for(transformer in decepticonsResults) {
                ratingsD += transformer.rating
                if(transformer.survived)
                    survivorsD++
            }

            if(survivorsA>survivorsD) {
                battlePagerAdapter.setWinners(autobotResults)
                battlePagerAdapter.setsurvivers(decepticonsResults)
            } else if (survivorsD > survivorsA) {
                battlePagerAdapter.setsurvivers(autobotResults)
                battlePagerAdapter.setWinners(decepticonsResults)
            } else if( ratingsA > ratingsD) {
                battlePagerAdapter.setWinners(autobotResults)
                battlePagerAdapter.setsurvivers(decepticonsResults)
            } else if( ratingsD > ratingsA) {
                battlePagerAdapter.setsurvivers(autobotResults)
                battlePagerAdapter.setWinners(decepticonsResults)
            } else {
                /**
                 * Good guys always win in a tiebreaker
                 */
                battlePagerAdapter.setWinners(autobotResults)
                battlePagerAdapter.setsurvivers(decepticonsResults)
            }
        }


        /**
         * Pager setup for winner and survivors list
         */
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = battlePagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val toolbar = findViewById<Toolbar>(R.id.battle_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun convertFighter(transformer: Transformer, survived: Boolean): FighterResult {
        return FighterResult().apply {
            this.icon = transformer.teamIcon
            this.rating = transformer.rating
            this.image = transformer.imageUrl
            this.name = transformer.name
            this.survived = survived
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}