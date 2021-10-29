package com.oui.parisiproject.presentation.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.oui.parisiproject.R
import com.oui.parisiproject.common.Keys
import com.oui.parisiproject.databinding.FragmentDiscoveryBinding
import com.oui.parisiproject.domain.model.discoveryModels.Category
import com.oui.parisiproject.domain.model.discoveryModels.Section
import com.oui.parisiproject.domain.model.discoveryModels.Weather
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DiscoveryFragment : Fragment() {

    private var weatherTemperature: Int = 0
    private lateinit var time: String
    private var weatherIcon: String = ""
    private lateinit var currentTime: Date
    private lateinit var currentDate: String

    private lateinit var binding: FragmentDiscoveryBinding

    private lateinit var categoryAdapter: CategoriesAdapter
    private lateinit var sectionAdapter: SectionsAdapter
    private lateinit var categoryList: ArrayList<Category>
    private lateinit var sectionList: ArrayList<Section>
    private lateinit var weatherList: ArrayList<Weather>

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var linearlayoutManager: LinearLayoutManager

    private val discoveryViewModel: DiscoveryViewModel by viewModels()
    @Inject
    lateinit var key: Keys


    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDiscoveryBinding.inflate(layoutInflater, container, false)

        setUpViews()
        clickListeners()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDiscoveryData()
    }

    private fun setUpViews(){

        categoryList = ArrayList()
        sectionList = ArrayList()
        weatherList = ArrayList()
        categoryAdapter = CategoriesAdapter(requireContext(), categoryList)
        sectionAdapter = SectionsAdapter(requireContext(), sectionList)

        //categories recycler
        binding.categoryRecyclerView.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(context, 6)
        binding.categoryRecyclerView.layoutManager = gridLayoutManager
        binding.categoryRecyclerView.adapter = categoryAdapter

        //sections recycler
        binding.sectionsRecycler.setHasFixedSize(true)
        linearlayoutManager = LinearLayoutManager(context)
        linearlayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.sectionsRecycler.layoutManager = linearlayoutManager
        binding.sectionsRecycler.adapter = sectionAdapter

        //set date and time
        currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("EEE dd MMM") // Set your date format
        currentDate = sdf.format(currentTime) // Get Date String according to date format
        time = SimpleDateFormat("HH:mm:ss").format(Date())

        binding.date.text = currentDate
        binding.hour.text = time

    }

    private fun getHashKey(): String{
        val api_key = "abcdef12345"
        val path = "/api/v2.3/marketing/home/"
        return key.md5(path+ key.getRequestTime()+ api_key)!!
    }

    private fun clickListeners(){

        //Search layout
        binding.searchBar.setOnClickListener(View.OnClickListener { v->

            findNavController().navigate(
                R.id.action_discover_to_searchFragment,
                null,
                navOptions { // Use the Kotlin DSL for building NavOptions
                    anim {
                        enter = android.R.animator.fade_in
                        exit = android.R.animator.fade_out
                    }
                }
            )

        })

        //Weather data
        binding.weatherLayout.setOnClickListener {

            if (weatherIcon!=null){
                setFragmentResult("discoveryDataKey", bundleOf("icon" to weatherIcon,
                "list" to weatherList,
                "time" to time,
                "date" to currentDate,
                "weatherTemperature" to weatherTemperature))
            }

            WeatherDetailsFragment().show(parentFragmentManager, "WeatherDetailsFragment")

            }

        }


    private fun getDiscoveryData(){

        getHashKey()

        discoveryViewModel.getDiscovery( "2.315,48.368")

        lifecycleScope.launchWhenCreated {

            discoveryViewModel.weatherData.collect {
                if (it.isLoading) {

                    binding.parent.visibility = View.GONE
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()

                }
                if (it.error.isNotBlank()) {
                    Toast.makeText(requireContext(),it.error, Toast.LENGTH_SHORT).show()
                }
                it.weatherData?.let {

                    binding.shimmerLayout.stopShimmer()
                    binding.parent.visibility = View.VISIBLE
                    binding.shimmerLayout.visibility = View.GONE

                    getWeatherData(it)

                }
            }
        }

        lifecycleScope.launchWhenCreated {

            discoveryViewModel.categoryData.collect { it ->
                if (it.isLoading) {

                    binding.parent.visibility = View.GONE
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()

                }
                if (it.error.isNotBlank()) {
                    Toast.makeText(requireContext(),it.error, Toast.LENGTH_SHORT).show()
                }
                it.categoriesData?.let {

                    binding.shimmerLayout.stopShimmer()
                    binding.parent.visibility = View.VISIBLE
                    binding.shimmerLayout.visibility = View.GONE

                    categoryAdapter.setData(it)

                }
            }
        }


        lifecycleScope.launchWhenCreated {

            discoveryViewModel.sectionData.collect { it ->
                if (it.isLoading) {

                    binding.parent.visibility = View.GONE
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()

                }
                if (it.error.isNotBlank()) {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }
                it.sectionData?.let {

                    binding.shimmerLayout.stopShimmer()
                    binding.parent.visibility = View.VISIBLE
                    binding.shimmerLayout.visibility = View.GONE

                    getSectionsData(it)

                }
            }
        }
    }

    private fun getWeatherData(list: List<Weather>){
        if (list.size !== 0) {

            val weatherSize: Int = list.size
            for (k in 0 until weatherSize - 1) {
                val weatherDate = list[k].date
                val weatherHour = list[k].hour
                weatherTemperature = list[k].temperature
                weatherIcon = list[k].icon

                weatherList.add(Weather(weatherDate, weatherHour, weatherIcon, weatherTemperature))

                binding.temperature.text = "$weatherTemperature°C"

                if (weatherIcon == "02n") {
                    binding.icon.setImageResource(R.drawable.ic_two)
                } else if (weatherIcon == "03n") {
                    binding.icon.setImageResource(R.drawable.ic_eight)
                } else if (weatherIcon == "02n") {
                    binding.icon.setImageResource(R.drawable.ic_eleven)
                } else if (weatherIcon == "01dn") {
                    binding.icon.setImageResource(R.drawable.ic_five)
                } else if (weatherIcon == "02n") {
                    binding.icon.setImageResource(R.drawable.ic_nine)
                } else if (weatherIcon == "02n") {
                    binding.icon.setImageResource(R.drawable.ic_one)
                } else if (weatherIcon == "02d") {
                    binding.icon.setImageResource(R.drawable.ic_six)
                } else if (weatherIcon == "02d") {
                    binding.icon.setImageResource(R.drawable.ic_ten)
                } else if (weatherIcon == "01d") {
                    binding.icon.setImageResource(R.drawable.ic_three)
                } else if (weatherIcon == "01d") {
                    binding.icon.setImageResource(R.drawable.ic_four)
                } else if (weatherIcon == "01d") {
                    binding.icon.setImageResource(R.drawable.ic_twelve)
                }
            }
        }
    }

    private fun getSectionsData(list: List<Section>){
        if(list.isNotEmpty()){

            //get all sections
            val sectionSize: Int = list.size

            for (j in 0 until sectionSize - 1) {
                val sectionTitle = list.get(j).section_title
                val sectionItemList = list.get(j).itemDTOS

                sectionList.add(Section(sectionItemList, sectionTitle))
            }

            sectionAdapter.setSectionData(sectionList)

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiscoveryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiscoveryFragment().apply {
                arguments = Bundle().apply {
                    putString("ARG_PARAM1", param1)
                    putString("ARG_PARAM2", param2)
                }
            }
    }
}