package com.oui.parisiproject.presentation.search

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
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
import com.google.gson.Gson
import com.oui.parisiproject.R
import com.oui.parisiproject.domain.model.searchModel.Activity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ActivitiesResultAdapter (val context: Context, list: List<Activity>, val which: String) :
    RecyclerView.Adapter<ActivitiesResultAdapter.MyViewHolder>() {

    private var list = list
    private val saveActivitiesList: ArrayList<Activity> = ArrayList()
    private lateinit var recentSharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var gson = Gson()
    private lateinit var json: String

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        recentSharedPreferences = context.getSharedPreferences("recent", Context.MODE_PRIVATE)
        editor = recentSharedPreferences.edit()

        val view: View

        if (which == "recent") {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recents_layout, parent, false)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.search_results_layout, parent, false)
        }

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: Activity = list[position]

        val pictureListSize = item.pictures.size
        var imgSrc = ""
        for (i in 0 until pictureListSize){
            imgSrc = item.pictures[i]
            Glide.with(context).load(imgSrc).into(holder.image)
        }

        holder.title.text = "" + item.locationName

        var distanceInKM: Float = 0F
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
            distanceInKM = loc1.distanceTo(loc2) / 1000

            holder.distance.text = "" + java.lang.String.format("%.1f", distanceInKM) + " km"

        } else {
            holder.distance.visibility = View.GONE
            holder.icon.visibility = View.GONE
        }
        var openCloseCheck: String = ""
        val closeOrOpen: String = ""

        val workingHoursSize: Int = item.workingHours.size
        for (n in 0 until workingHoursSize) {

            val closeOrOpen: Boolean = item.workingHours.get(n).closed!!

            if (!closeOrOpen) {
                val c = Calendar.getInstance()
                val dayOfWeek = c[Calendar.DAY_OF_WEEK]
                var day = dayOfWeek - 1
                if (day == 0) {
                    day = 7
                }
                val dayOfTheWeek: Int = item.workingHours.get(n).dayOfWeek!!

                if (dayOfTheWeek == day) {
                    val openTime1: String =
                        item.workingHours.get(n)
                            .openTime1!!
                    val closeTime1: String =
                        item.workingHours.get(n)
                            .closeTime1!!
                    var openTime2 = ""
                    var closeTime2 = ""
                    if (item.workingHours.get(position)
                            .openTime2 != null && item.workingHours.get(n).openTime2 != null
                    ) {
                        openTime2 =
                            item.workingHours.get(n)
                                .openTime2.toString()
                        closeTime2 =
                            item.workingHours.get(n)
                                .openTime2.toString()
                    }
                    val currentTime =
                        SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(
                            Date()
                        )
                    if (currentTime.compareTo(openTime1) > 0 && currentTime.compareTo(closeTime1) < 0) {

                        openCloseCheck = "open"
                    } else {
                        openCloseCheck = "close"
                        if (openTime2 != "" && closeTime2 != "") {
                            openCloseCheck =
                                if (currentTime.compareTo(openTime2) > 0 && currentTime.compareTo(
                                        closeTime2
                                    ) < 0
                                ) {
                                    "open"
                                } else {
                                    "close"
                                }
                        }
                    }
                }
            }

        }

        if (openCloseCheck == "open") {
            holder.hour.setTextColor(Color.GREEN)
            holder.hour.text = "" + openCloseCheck
        } else if (openCloseCheck == "close") {
            holder.hour.setTextColor(Color.RED)
            holder.hour.text = "" + openCloseCheck
        }

        holder.name.text = "" + item.displayCatSubCatName
        saveActivitiesList.add(
            Activity(
                item.id,
                item.locationName,
                "",
                item.pictures,
                item.pinActive,
                imgSrc,
                item.displayCatSubCatName,
                distanceInKM,
                item.workingHours,
                item.lat,
                item.lng
            )
        )

        if (gson != null) {
            json = gson.toJson(saveActivitiesList)
            editor.putString("recentResults", json)
            editor.commit()
        }

        holder.parentLayout.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return if(which == "recent") 3
        else
            list.size
    }

    fun setActivitiesData(list: List<Activity>)
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