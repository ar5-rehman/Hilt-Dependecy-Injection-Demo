package com.oui.parisiproject.presentation.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.oui.parisiproject.R
import com.oui.parisiproject.domain.model.searchModel.Category
import com.oui.parisiproject.domain.model.searchModel.SubCategory

class SubCategoryResultAdapter  (val context: Context, private var list: List<SubCategory>) :
    RecyclerView.Adapter<SubCategoryResultAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_subcategory_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: SubCategory = list[position]
        holder.categoryChip.setText(item.name)
        holder.categoryChip.setOnClickListener {
            /*val fragment =
                LocationFragment(checkCategoryOrSubCategory, context, activitiesPojo.getId())
            val transaction: FragmentTransaction =
                activity.getSupportFragmentManager().beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()*/
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setSubcategoryData(list: List<SubCategory>)
    {
        this.list=list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryChip: Chip = itemView.findViewById(R.id.categoryChip)

    }
}

