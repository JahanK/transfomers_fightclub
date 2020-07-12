package com.jnkhan.transfomersfightclub.view.main

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
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

class FighterAdapter(
    var context: Context,
    var transformers: List<Transformer>,
    val onTransformerDeletion: (Transformer) -> Unit
) :
    RecyclerView.Adapter<FighterAdapter.TransformerHolder>() {

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

        holder.str.text =
            (context.getString(R.string.listitem_strength) + " " + transformer.strength)
        holder.ntl.text =
            (context.getString(R.string.listitem_intelligence) + " " + transformer.intelligence)
        holder.spd.text = (context.getString(R.string.listitem_speed) + " " + transformer.speed)
        holder.end.text =
            (context.getString(R.string.listitem_endurance) + " " + transformer.endurance)
        holder.rnk.text = (context.getString(R.string.listitem_rank) + " " + transformer.rank)
        holder.crg.text = (context.getString(R.string.listitem_courage) + " " + transformer.courage)
        holder.fpr.text =
            (context.getString(R.string.listitem_firepower) + " " + transformer.firepower)
        holder.skl.text = (context.getString(R.string.listitem_skill) + " " + transformer.skill)

        holder.image.setImageResource(if (transformer.team.compareTo("A") == 0) R.drawable.svg_autobots else R.drawable.svg_decepticons)
        holder.rtg.text = (context.getString(R.string.listItem_rating) + " " + transformer.rating)

        holder.delete.setOnClickListener {onTransformerDeletion(transformer)}

        Glide.with(context).load(Uri.parse(transformer.teamIcon)).into(holder.icon)

        Glide.with(context)
            .load(Uri.parse(transformer.imageUrl.replace(" ", "_")))
            .into(holder.image)
    }

    class TransformerHolder(listItem: View) : RecyclerView.ViewHolder(listItem) {

        var card = listItem.findViewById<CardView>(R.id.listitem_transformer)

        var image = listItem.findViewById<ImageView>(R.id.listitem_transformer_image)
        var nam = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_name)
        var rtg = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_rating)

        var str = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_strength)
        var ntl = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_intelligence)
        var spd = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_speed)
        var end = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_endurance)
        var skl = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_skill)
        var crg = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_courage)
        var fpr = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_firepower)
        var rnk = listItem.findViewById<MaterialTextView>(R.id.listitem_transformer_rank);

        var icon = listItem.findViewById<ImageView>(R.id.listitem_transformer_icon)
        var delete = listItem.findViewById<ImageButton>(R.id.listitem_transformer_recycle)
    }
}