package com.oui.parisiproject.presentation.discovery

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.oui.parisiproject.R
import com.oui.parisiproject.domain.model.discoveryModels.Category

class CategoriesAdapter (private var context: Context,  private var list: List<Category>): RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: RelativeLayout = itemView.findViewById(R.id.layout)
        var categoryImage: ImageView = itemView.findViewById(R.id.categoryImage)
        var name: TextView = itemView.findViewById(R.id.name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val categoryItem: Category = list[position]

        val requestBuilder: com.bumptech.glide.RequestBuilder<PictureDrawable> = GlideToVectorYou
            .init()
            .with(context)
            .requestBuilder

        requestBuilder
            .load(categoryItem.icon)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(
                RequestOptions()
                    .centerCrop()
            )
            .into(holder.categoryImage)


        holder.name.setText(categoryItem.name)

        holder.layout.setOnClickListener {
            /*MainActivity.bottomNavigationView.setSelectedItemId(R.id.location)
            //MainActivity.bottomNavigationView.getMenu().getItem(3).setIcon(R.drawable.ic_place_active);
            val fragment = LocationFragment(1, context, discoveryPojo.getId())
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

    fun setData(list: List<Category>)
    {
        this.list=list
        notifyDataSetChanged()
    }

}