package com.oui.parisiproject.presentation.discovery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.oui.parisiproject.R
import com.oui.parisiproject.data.model.discoveryModel.ItemDTO
import com.oui.parisiproject.domain.model.discoveryModels.Section

class SectionsItemAdapter(val item: Section, val list: List<ItemDTO>, val context: Context) :
    RecyclerView.Adapter<SectionsItemAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.discovery_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val discoveryPojo: ItemDTO = list[position]
        Glide.with(context).load(discoveryPojo.picture).into(holder.imageSrc)
        holder.txtOne.setText(discoveryPojo.name)
        holder.layout.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: RelativeLayout = itemView.findViewById(R.id.layout)
        var imageSrc: ImageView = itemView.findViewById(R.id.imageSrc)
        var txtOne: TextView = itemView.findViewById(R.id.txtOne)
        var materialCardView: MaterialCardView = itemView.findViewById(R.id.rLayout)

    }
}
