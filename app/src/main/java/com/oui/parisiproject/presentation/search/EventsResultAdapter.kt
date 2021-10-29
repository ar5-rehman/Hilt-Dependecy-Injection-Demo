package com.oui.parisiproject.presentation.search

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oui.parisiproject.R
import com.oui.parisiproject.domain.model.searchModel.Event

class EventsResultAdapter (private val context: Context,  private var list: List<Event>) :
    RecyclerView.Adapter<EventsResultAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_results_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: Event = list[position]

        val pictureListSize = item.picture.size
        for (i in 0 until pictureListSize){
            Glide.with(context).load(item.picture[i].picture).into(holder.image)
        }

        holder.title.text = "" + item.name
        if (isLocationEnabled(context)) {
            holder.distance.text =
                "" + java.lang.String.format("%.1f", item.distance) + " km"
        } else {
            holder.distance.visibility = View.GONE
            holder.icon.visibility = View.GONE
        }

        holder.hour.visibility = View.GONE

        holder.name.text = "" + item.displayCatSubCatName
        /*SearchFragment.saveList.add(
            ResultModel(
                null,
                item.activePin,
                item.id,
                item.imgSrc,
                item.title,
                "",
                item.hour,
                item.distance,
                item.name
            )
        )*/

        if (isLocationEnabled(context)) {

            //calculating distance
            val lat: Double = item.lat!!
            val lng: Double = item.lng!!
            val loc1 = Location("")

            /* if (MainActivity.mylat !== 0.0) {
              loc1.latitude = 33.994648
              loc1.longitude = 72.910622
          }*/

            loc1.latitude = 33.994648
            loc1.longitude = 72.910622

            val loc2 = Location("")
            loc2.latitude = lat
            loc2.longitude = lng
            val distanceInKM = loc1.distanceTo(loc2) / 1000

            holder.distance.text = "" + java.lang.String.format("%.1f", distanceInKM) + " km"

        } else {
            holder.distance.visibility = View.GONE
            holder.icon.visibility = View.GONE
        }

        holder.parentLayout.setOnClickListener {
            /* val intent = Intent(context, ActivitiesDetailsActivity::class.java)
             intent.putExtra("id", item.id)
             context.startActivity(intent)*/
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setEventsData(list: List<Event>)
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

    private fun isLocationEnabled(mContext: Context): Boolean {
        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}