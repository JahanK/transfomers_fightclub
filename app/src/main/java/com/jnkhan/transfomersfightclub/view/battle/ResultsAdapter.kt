package com.jnkhan.transfomersfightclub.view.battle

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.textview.MaterialTextView
import com.jnkhan.transfomersfightclub.R
import com.jnkhan.transfomersfightclub.store.FighterResult
import com.jnkhan.transfomersfightclub.store.Transformer

class ResultsAdapter(
    var context: Context,
    var transformers: ArrayList<FighterResult>
) :
    RecyclerView.Adapter<ResultsAdapter.TransformerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransformerHolder {

        val transformerHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_results, parent, false)

        return TransformerHolder(
            transformerHolder
        )
    }

    override fun getItemCount(): Int {
        return transformers.size
    }

    override fun onBindViewHolder(holder: TransformerHolder, position: Int) {

        var transformer = transformers.get(position)

        holder.nam.text = transformer.name.replace("_", " ")

        Glide.with(context)
            .asBitmap()
            .load(Uri.parse(transformer.image.replace(" ","_")))
            .listener(object : RequestListener<Bitmap> {
                override fun onResourceReady(
                    resource: Bitmap?, model: Any?, target: Target<Bitmap>?,
                    dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean {
                    if (resource == null) return false

                    val palette = Palette.from(resource).generate()
                    val swatch = palette.getLightVibrantColor(palette.getMutedColor(0))
                    holder.main.setBackgroundColor(swatch)

                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?, model: Any?, target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .into(holder.image)
    }

    class TransformerHolder(listItem: View) : RecyclerView.ViewHolder(listItem) {

        var main = listItem.findViewById<ConstraintLayout>(R.id.listitem_transformer_container)

        var image = listItem.findViewById<ImageView>(R.id.listitem_transformer_image)
        var nam = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_title)
    }
}