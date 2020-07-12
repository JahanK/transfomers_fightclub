package com.jnkhan.transfomersfightclub.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import com.jnkhan.transfomersfightclub.R
import com.jnkhan.transfomersfightclub.store.Transformer
import com.jnkhan.transfomersfightclub.view.selection.SelectionActivity
import com.jnkhan.transfomersfightclub.view.selection.SelectionAdapter
import com.jnkhan.transfomersfightclub.viewmodel.TFCViewModel
import com.jnkhan.transfomersfightclub.viewmodel.TFCViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TFCViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            TFCViewModelFactory(this.application)
        ).get(TFCViewModel::class.java)

        setupHero()
        setupFabAdd()
        setupFabBattle()

        setupFighterList()
    }

    private fun setupFighterList() {

        val recyclerView = findViewById<RecyclerView>(R.id.list_fighter)

        val gridManager = GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridManager

        viewModel.fighters.observe(this, Observer { transformers ->

            recyclerView.adapter = FighterAdapter(this.applicationContext, transformers)
            { deletion -> onDeleteTransformer(deletion) }
        })

    }

    private fun onDeleteTransformer(transformer: Transformer) {
        viewModel.deleteTransformer(transformer)
    }

    private fun setupHero() {
        val image = findViewById<ImageView>(R.id.background_hero)
        Glide.with(applicationContext)
            .load(Uri.parse(resources.getString(R.string.url_image_hero)))
            .into(image)
    }

    private fun setupFabBattle() {
        val fabBattle: FloatingActionButton = findViewById(R.id.fab_battle)
        fabBattle.setOnClickListener { view ->
            Snackbar.make(view, resources.getString(R.string.snackbar_empty), Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun setupFabAdd() {
        val appBar = findViewById<AppBarLayout>(R.id.appBar)

        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add)
        fabAdd.setOnClickListener {
            kotlin.run {
                appBar.setExpanded(false)

                val intent = Intent(this, SelectionActivity::class.java)
                startActivity(intent)
            }
        }
    }
}