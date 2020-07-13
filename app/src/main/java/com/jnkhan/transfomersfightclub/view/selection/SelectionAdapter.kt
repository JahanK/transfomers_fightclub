package com.jnkhan.transfomersfightclub.view.selection

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
import com.jnkhan.transfomersfightclub.store.Transformer

class SelectionAdapter(
    var context: Context,
    var transformers: ArrayList<Transformer>,
    val onSelection: (Transformer) -> Unit
) :
    RecyclerView.Adapter<SelectionAdapter.TransformerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransformerHolder {

        val transformerHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_transformer, parent, false)

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

        holder.str.text = transformer.strength.toString()
        holder.ntl.text = transformer.intelligence.toString()
        holder.spd.text = transformer.speed.toString()
        holder.end.text = transformer.endurance.toString()
        holder.rnk.text = transformer.rank.toString()
        holder.crg.text = transformer.courage.toString()
        holder.fpr.text = transformer.firepower.toString()
        holder.skl.text = transformer.skill.toString()
        holder.rtg.text = transformer.rating.toString()

        holder.delete.visibility = View.GONE
        holder.edit.visibility = View.GONE

        holder.main.setOnClickListener { onSelection(transformer) }

        Glide.with(context)
            .asBitmap()
            .load(Uri.parse(transformer.imageUrl))
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
        var rtg = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_rating)

        var str = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_strength)
        var ntl = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_intelligence)
        var spd = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_speed)
        var end = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_endurance)
        var skl = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_skill)
        var crg = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_courage)
        var fpr = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_firepower)
        var rnk = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_rank);

        var edit = listItem.findViewById<ImageButton>(R.id.listitem_transformer_edit)
        var delete = listItem.findViewById<ImageButton>(R.id.listitem_transformer_delete)
    }
}