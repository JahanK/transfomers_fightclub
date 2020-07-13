package com.jnkhan.transfomersfightclub.view.edit

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import com.jnkhan.transfomersfightclub.R
import com.jnkhan.transfomersfightclub.store.Transformer
import com.jnkhan.transfomersfightclub.view.main.MainActivity
import com.jnkhan.transfomersfightclub.viewmodel.TfcViewModel
import com.jnkhan.transfomersfightclub.viewmodel.TfcViewModelFactory

class EditActivity : AppCompatActivity() {

    val VALUE_SLIDER_MAX = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val transformerString = intent.getStringExtra(MainActivity.KEY_TRANSFORMER_EDIT)
        val gson = Gson()
        val transformer = gson.fromJson<Transformer>(transformerString, Transformer::class.java)

        var main = findViewById<ConstraintLayout>(R.id.listitem_transformer_container)

        var image = findViewById<ImageView>(R.id.listitem_transformer_image)
        var nam = findViewById<MaterialTextView>(R.id.listitem_transformer_title)
        nam.text = transformer.name

        var team = findViewById<MaterialTextView>(R.id.listitem_transformer_subtitle)
        team.text =
            if (transformer.team.compareTo(TfcViewModel.AUTOBOT_CHARACTER) == 0) resources.getString(
                R.string.listitem_allegience_autobot
            )
            else resources.getString(R.string.listitem_allegience_decepticon)

        var rtg = findViewById<MaterialTextView>(R.id.listitem_transformer_rating)
        rtg.text = transformer.rating.toString()

        var str = findViewById<Spinner>(R.id.listitem_transformer_strength)
        str.setSelection(transformer.strength - 1)

        var ntl = findViewById<Spinner>(R.id.listitem_transformer_intelligence)
        ntl.setSelection(transformer.intelligence - 1)

        var spd = findViewById<Spinner>(R.id.listitem_transformer_speed)
        spd.setSelection(transformer.speed - 1)

        var end = findViewById<Spinner>(R.id.listitem_transformer_endurance)
        end.setSelection(transformer.endurance - 1)

        var skl = findViewById<Spinner>(R.id.listitem_transformer_skill)
        skl.setSelection(transformer.skill - 1)

        var crg = findViewById<Spinner>(R.id.listitem_transformer_courage)
        crg.setSelection(transformer.courage - 1)

        val fpr = findViewById<Spinner>(R.id.listitem_transformer_firepower)
        fpr.setSelection(transformer.firepower - 1)

        var rnk = findViewById<Spinner>(R.id.listitem_transformer_rank);
        rnk.setSelection(transformer.rank - 1)


        var icon = findViewById<ImageView>(R.id.listitem_transformer_icon)
        var navUp = findViewById<ImageButton>(R.id.listitem_transformer_navigate_up)
        navUp.setOnClickListener {
            onBackPressed()
            finish()
        }

        val viewModel =
            ViewModelProvider(this, TfcViewModelFactory(application)).get(
                TfcViewModel::class.java
            )

        var save = findViewById<ImageButton>(R.id.listitem_transformer_save)
        save.setOnClickListener {
            if (!viewModel.checkInternet()) {
                Toast.makeText(
                    this,
                    getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                transformer.strength = str.selectedItemPosition + 1
                transformer.intelligence = ntl.selectedItemPosition + 1
                transformer.endurance = end.selectedItemPosition + 1
                transformer.speed = spd.selectedItemPosition + 1
                transformer.skill = skl.selectedItemPosition + 1
                transformer.rank = rnk.selectedItemPosition + 1
                transformer.firepower = fpr.selectedItemPosition + 1
                transformer.courage = crg.selectedItemPosition + 1

                viewModel.updateTransformer(transformer)
                Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
            }
        }

        Glide.with(this).load(Uri.parse(transformer.teamIcon)).into(icon)

        Glide.with(this)
            .load(Uri.parse(transformer.imageUrl.replace(" ", "_")))
            .into(image)


        /**
         * To update the rating on change in relevant attributes
         */
        var onAttributeItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                transformer.strength = str.selectedItemPosition + 1
                transformer.intelligence = ntl.selectedItemPosition + 1
                transformer.endurance = end.selectedItemPosition + 1
                transformer.speed = spd.selectedItemPosition + 1
                transformer.firepower = fpr.selectedItemPosition + 1

                rtg.text = transformer.rating.toString()
            }

        }

        str.onItemSelectedListener = onAttributeItemSelectedListener
        ntl.onItemSelectedListener = onAttributeItemSelectedListener
        fpr.onItemSelectedListener = onAttributeItemSelectedListener
        end.onItemSelectedListener = onAttributeItemSelectedListener
        spd.onItemSelectedListener = onAttributeItemSelectedListener
    }
}