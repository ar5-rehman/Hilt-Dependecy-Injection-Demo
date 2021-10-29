package com.oui.parisiproject.presentation.discovery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oui.parisiproject.R
import com.oui.parisiproject.domain.model.discoveryModels.Section

class SectionsAdapter(private val context: Context, private var list: List<Section>) :
    RecyclerView.Adapter<SectionsAdapter.MyViewHolder>() {

    private var discoveryAdapter: RecyclerView.Adapter<*>? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.sections_layout, parent, false)
        val myViewHolder = MyViewHolder(view)
        myViewHolder.sectionRecycler.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager!!.orientation = RecyclerView.HORIZONTAL
        myViewHolder.sectionRecycler.layoutManager = linearLayoutManager
        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: Section = list[position]
        holder.sectionTitle.setText(item.section_title)

        discoveryAdapter = SectionsItemAdapter(item, item.itemDTOS, context)
        holder.sectionRecycler.adapter = discoveryAdapter
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setSectionData(list: List<Section>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sectionTitle: TextView = itemView.findViewById(R.id.sectionTitle)
        var sectionRecycler: RecyclerView = itemView.findViewById(R.id.sectionsRecycler)

    }
}