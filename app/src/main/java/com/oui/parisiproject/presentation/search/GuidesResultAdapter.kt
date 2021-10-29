package com.oui.parisiproject.presentation.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.oui.parisiproject.R
import com.oui.parisiproject.domain.model.searchModel.Guide

class GuidesResultAdapter (val context: Context, private var list: List<Guide>) :
    RecyclerView.Adapter<GuidesResultAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_results_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: Guide = list[position]

        Glide.with(context).load(item.picture).into(holder.image)
        holder.title.text = "" + item.name
        holder.distance.visibility = View.GONE
        holder.hour.visibility = View.GONE
        holder.name.visibility = View.GONE
        holder.icon.visibility = View.GONE

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setGuidesData(list: List<Guide>)
    {
        this.list=list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parentLayout: RelativeLayout = itemView.findViewById(R.id.parentLayout)
        var image: ImageView = itemView.findViewById(R.id.imgSrc)
        var icon: ImageView = itemView.findViewById(R.id.icon)
        var title: TextView = itemView.findViewById(R.id.title)
        var distance: TextView = itemView.findViewById(R.id.distance)
        var hour: TextView = itemView.findViewById(R.id.hour)
        var name: TextView = itemView.findViewById(R.id.name)

    }
}