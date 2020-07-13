package com.jnkhan.transfomersfightclub.view.main

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
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
import com.jnkhan.transfomersfightclub.view.edit.EditActivity
import com.jnkhan.transfomersfightclub.view.selection.SelectionActivity
import com.jnkhan.transfomersfightclub.viewmodel.TfcViewModel
import com.jnkhan.transfomersfightclub.viewmodel.TfcViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TfcViewModel
    private lateinit var adapter: FighterAdapter

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

        val gridManager = GridLayoutManager(
            this,
            resources.getInteger(R.integer.recycler_columns),
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.layoutManager = gridManager
        adapter =
            FighterAdapter(this.applicationContext) { action, transf -> onActionTaken(action, transf) }
        recyclerView.adapter = adapter

        viewModel.fighters.observe(this, Observer { transformers ->
            adapter.setTransformers(transformers as ArrayList<Transformer>)
        })

    }

    companion object {
        val VALUE_EDIT = 0
        val VALUE_DELETE = 1
        val KEY_TRANSFORMER_EDIT = "edit"
    }

    private fun onActionTaken(action : Int, transformer: Transformer) {
        when (action) {
            VALUE_EDIT -> onEditTransformer(transformer)
            VALUE_DELETE -> onDeleteTransformer(transformer)
        }
    }

    private fun onDeleteTransformer(transformer: Transformer) {
        if (!viewModel.checkInternet()) {
            Toast.makeText(
                this,
                getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        viewModel.deleteTransformer(transformer)
    }

    private fun onEditTransformer(transformer: Transformer) {
        kotlin.run {
            val editIntent = Intent(this, EditActivity::class.java)
            var gson = Gson()

            editIntent.putExtra(KEY_TRANSFORMER_EDIT, gson.toJson(transformer))
            startActivity(editIntent)
        }
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