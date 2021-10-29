package com.oui.parisiproject.presentation.location

import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oui.parisiproject.databinding.FragmentLocationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding
   /* private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var key: Keys
    private lateinit var linearlayoutManager: LinearLayoutManager

    private val activityList: ArrayList<ResultModel>? = null
    private lateinit var locationScreenList: ArrayList<ResultModel>
    private var imagesList: ArrayList<String>? = null
    private var activitiesDetailsAdapter: ActivitiesAdapter? = null
    private val activitiesLinearLayoutManager: LinearLayoutManager? = null
    private val img: List<ImageView>? = null
    private  var imgActive: MutableList<ImageView?>? = null
    private val icon: Bitmap? = null
    private  var actveIcon: Bitmap? = null
    private val pin: String? = null
    private  var pinActive: String? = null
    private val imageHolder: View? = null
    private val pinID = 0
    private val lat: Double? = null
    private  var lng: Double? = null
    //var mapActivitiesPojo: ArrayList<MapActivitiesPojo>? = null

    private val request_time: String? = null
    private  var hash: String? = null
    private  var tagg: String = ""
    private  var sort: String? = ""
    private val activityID = 0
    private  var openNow:Int = 8
    private  var openOn:Int = 8
    private var imgURL: String? = null
    private val checkCategoryOrSubCategory = 0
    private val categoriesID = ""
    private  var subCategoryID: String? = ""
    private  var check: String? = ""
    private var isLastPage = false
    private  var isLoading: Boolean = false
    private var currentPage = 1
    private var nextDataLink: String? = null*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLocationBinding.inflate(layoutInflater, container, false)

       /* setupInitView()
        getActivitiesData()*/

        return binding.root
    }

    /*private fun setupInitView(){
        locationScreenList = ArrayList()

        activitiesDetailsAdapter = ActivitiesAdapter(requireContext())
        binding.detailsRecycler.adapter = activitiesDetailsAdapter

        linearlayoutManager = LinearLayoutManager(requireContext())
        linearlayoutManager.orientation = RecyclerView.VERTICAL
        binding.detailsRecycler.layoutManager = linearlayoutManager

        binding.detailsRecycler.addOnScrollListener(object: PaginationScrollListener(linearlayoutManager) {
            protected override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                loadNextPage()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })
    }

    private fun loadNextPage(){

        val cur = nextDataLink!!.substring(68, nextDataLink!!.length)

        mainViewModel.getActivitiesNextPage(cur, getHashKey(), getAPIKey())

        lifecycleScope.launchWhenStarted {
            mainViewModel._getActivitiesNextPageStateFlow.collect {it->
                when(it){
                    is ApiState.Loading->{

                    }
                    is ApiState.Failure -> {

                    }
                    is ApiState.ActivitiesSuccess->{
                        if(it.data.isSuccessful){

                            if (it.data.body()!!.results!!.size !== 0) {
                                locationScreenList = ArrayList()
                                activitiesDetailsAdapter!!.removeLoadingFooter()
                                isLoading = false
                                returnData(locationScreenList, it.data.body()!!)
                            }

                        }
                    }
                    is ApiState.Empty->{

                    }
                    else -> {

                    }
                }
            }
        }

    }
*/

   /* fun returnData(locationScreenList: ArrayList<ResultModel>, activitiesResult: GetActivitiesPojo) {

        nextDataLink = activitiesResult.next

        *//*binding.shimmerLayout.stopShimmer()
        binding.parentLayout.visibility = View.VISIBLE
        binding.shimmerLayout.setVisibility(View.GONE)*//*

        val activitiesSize: Int = activitiesResult.results!!.size
        for (i in 0..activitiesSize - 1) {
            imagesList = ArrayList()
            imgURL = ""
            val idd: Int = activitiesResult.results!!.get(i).id!!
            val title: String = activitiesResult.results!!.get(i).locationName!!
            val subTitle: String = activitiesResult.results!!.get(i).name!!
            val displayCatSubCat: String =
                activitiesResult.results!!.get(i).displayCatSubCat!!.name!!
            val lat: Double = activitiesResult.results!!.get(i).lat!!
            val lng: Double = activitiesResult.results!!.get(i).lng!!
            val activePin: String = activitiesResult.results!!.get(i).displayCatSubCat!!.iconActive!!
            val loc1 = Location("")

           *//* if (MainActivity.mylat !== 0.0) {
                loc1.latitude = MainActivity.mylat
                loc1.longitude = MainActivity.mylng
            }*//*

            loc1.latitude = 33.994648
            loc1.longitude = 72.910622

            val loc2 = Location("")
            loc2.latitude = lat
            loc2.longitude = lng
            val distanceinKM = loc1.distanceTo(loc2) / 1000
            val imgsSize: Int = activitiesResult!!.results!!.get(i).pictures!!.size
            for (j in 0..imgsSize - 1) {
                imgURL = activitiesResult!!.results!!.get(i)!!.pictures!!.get(j)
                imagesList!!.add(imgURL!!)
            }
            var openOrClose = ""
            val workingHoursSize: Int =
                activitiesResult!!.results!!.get(i).workingHours!!.size
            for (n in 0..workingHoursSize - 1) {
                val closeOrOpenCheck: Boolean =
                    activitiesResult!!.results!!.get(i).workingHours!!.get(n).closed!!
                if (!closeOrOpenCheck) {
                    val c = Calendar.getInstance()
                    val dayOfWeek = c[Calendar.DAY_OF_WEEK]
                    var day = dayOfWeek - 1
                    if (day == 0) {
                        day = 7
                    }
                    val dayOfTheWeek: Int =
                        activitiesResult!!.results!!.get(i).workingHours!!.get(n).dayOfWeek!!
                    if (dayOfTheWeek == day) {
                        var openTime1 = ""
                        var closeTime1 = ""
                        if (activitiesResult!!.results!!.get(i).workingHours!!.get(n)
                                .openTime1 != null && activitiesResult!!.results!!.get(i)
                                .workingHours!!.get(n)!!.closeTime1 != null
                        ) {
                            openTime1 =
                                activitiesResult!!.results!!.get(i)!!.workingHours!!.get(n)
                                    .openTime1!!
                            closeTime1 =
                                activitiesResult!!.results!!.get(i).workingHours!!.get(n)
                                    .closeTime1!!
                        }
                        var openTime2 = ""
                        var closeTime2 = ""
                        if (activitiesResult!!.results!!.get(i).workingHours!!.get(n)
                                .openTime2 != null && activitiesResult!!.results!!.get(i)
                                .workingHours!!.get(n).closeTime2!= null
                        ) {
                            openTime2 =
                                activitiesResult!!.results!!.get(i).workingHours!!.get(n)
                                    .openTime2.toString()
                            closeTime2 =
                                activitiesResult!!.results!!.get(i).workingHours!!.get(n)
                                    .openTime2.toString()
                        }
                        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(
                            Date()
                        )
                        if (currentTime.compareTo(openTime1) > 0 && currentTime.compareTo(closeTime1) < 0) {
                            openOrClose = "open"
                        } else {
                            openOrClose = "close"
                            if (openTime2 != "" && closeTime2 != "") {
                                openOrClose =
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
            locationScreenList.add(
                ResultModel(
                    imagesList,
                    activePin,
                    idd,
                    imgURL,
                    title,
                    subTitle,
                    openOrClose,
                    distanceinKM,
                    displayCatSubCat
                )
            )
        }
        activitiesDetailsAdapter!!.addAll(locationScreenList)
        if (activitiesResult.next != null) {
            if (!activitiesResult.next.equals("")) {
                activitiesDetailsAdapter!!.addLoadingFooter()
            } else {
                isLastPage = true
            }
        } else {
            isLastPage = true
        }
    }

    private fun getAPIKey(): String{

        return key.getRequestTime()!!
    }

    private fun getHashKey(): String{
        val api_key = "abcdef12345"
        val path = "/api/v2.3/activities/"
        return key.md5(path+""+ getAPIKey()+""+api_key)!!
    }

    private fun getActivitiesData(){

        mainViewModel.getActivities(getHashKey(), getAPIKey())

        lifecycleScope.launchWhenStarted {
            mainViewModel._getActivitiesStateFlow.collect {it->
                when(it){
                    is ApiState.Loading->{

                    }
                    is ApiState.Failure -> {

                    }
                    is ApiState.ActivitiesSuccess->{
                        if(it.data.isSuccessful){

                            if (it.data.body()!!.results!!.size !== 0) {
                                locationScreenList = ArrayList()
                                returnData(locationScreenList, it.data.body()!!)
                            }

                        }
                    }
                    is ApiState.Empty->{

                    }
                    else -> {

                    }
                }
            }
        }

    }*/

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LocationFragment().apply {
                arguments = Bundle().apply {
                    putString("ARG_PARAM1", param1)
                    putString("ARG_PARAM2", param2)
                }
            }
    }
}