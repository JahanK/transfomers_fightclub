package com.jnkhan.transfomersfightclub.view

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.jnkhan.transfomersfightclub.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var image = findViewById<ImageView>(R.id.background_image)

        Glide.with(applicationContext)
            .load(Uri.parse("https://i.pinimg.com/originals/fc/1c/f6/fc1cf6acc84ad1dc76c62be24e19409f.jpg"))
            .into(image)

        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}