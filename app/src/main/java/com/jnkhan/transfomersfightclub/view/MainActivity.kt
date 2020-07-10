package com.jnkhan.transfomersfightclub.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.jnkhan.transfomersfightclub.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var appBar = findViewById<AppBarLayout>(R.id.appBar)

        var image = findViewById<ImageView>(R.id.background_hero)
        Glide.with(applicationContext)
            .load(Uri.parse(resources.getString(R.string.url_image_hero)))
            .into(image)

        val fabBattle: FloatingActionButton = findViewById(R.id.fab_battle)
        fabBattle.setOnClickListener { view ->
            Snackbar.make(view, resources.getString(R.string.snackbar_empty), Snackbar.LENGTH_LONG).show()
        }

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