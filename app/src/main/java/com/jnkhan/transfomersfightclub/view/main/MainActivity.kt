package com.jnkhan.transfomersfightclub.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.jnkhan.transfomersfightclub.R
import com.jnkhan.transfomersfightclub.store.Transformer
import com.jnkhan.transfomersfightclub.view.selection.SelectionActivity
import com.jnkhan.transfomersfightclub.viewmodel.TfcViewModel
import com.jnkhan.transfomersfightclub.viewmodel.TfcViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TfcViewModel
    private lateinit var adapter : FighterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            TfcViewModelFactory(this.application)
        ).get(TfcViewModel::class.java)

        setupFabAdd()
        setupFabBattle()

        setupFighterList()
    }

    private fun setupFighterList() {

        val recyclerView = findViewById<RecyclerView>(R.id.list_fighter)

        val gridManager = GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridManager
        adapter = FighterAdapter(this.applicationContext){ deletion -> onDeleteTransformer(deletion) }
        recyclerView.adapter = adapter

        viewModel.fighters.observe(this, Observer { transformers ->

            Log.e("TAT", transformers.size.toString())
            adapter.setTransformers(transformers as ArrayList<Transformer>)
        })

    }

    private fun onDeleteTransformer(transformer: Transformer) {
        viewModel.deleteTransformer(transformer)
    }

    private fun setupFabBattle() {
        val fabBattle: FloatingActionButton = findViewById(R.id.fab_battle)
        fabBattle.setOnClickListener { view ->
            Snackbar.make(view, resources.getString(R.string.snackbar_empty), Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun setupFabAdd() {

        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add)
        fabAdd.setOnClickListener {
            kotlin.run {

                val intent = Intent(this, SelectionActivity::class.java)
                startActivity(intent)
            }
        }
    }
}