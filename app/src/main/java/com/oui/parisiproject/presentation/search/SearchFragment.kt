package com.oui.parisiproject.presentation.search

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oui.parisiproject.common.Keys
import com.oui.parisiproject.data.model.searchModel.PictureDTO
import com.oui.parisiproject.databinding.FragmentSearchBinding
import com.oui.parisiproject.domain.model.searchModel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    private val searchViewModel: SearchViewModel by viewModels()
    @Inject
    lateinit var key: Keys

    private lateinit var categoriesAdapter: CategoryResultAdapter
    private lateinit var subCategoriesAdapter: SubCategoryResultAdapter
    private lateinit var activitiesAdapter: ActivitiesResultAdapter
    private lateinit var guidesAdapter: GuidesResultAdapter
    private lateinit var eventsAdapter: EventsResultAdapter

    private lateinit var recentLinearLayoutManager: LinearLayoutManager
    private lateinit var categoriesLinearLayoutManager: LinearLayoutManager
    private lateinit var subCategoriesLinearLayoutManager: LinearLayoutManager
    private lateinit var activitiesLinearLayoutManager: LinearLayoutManager
    private lateinit var eventsLinearLayoutManager: LinearLayoutManager
    private lateinit var guideLinearLayoutManager: LinearLayoutManager

    private val recentList: ArrayList<Activity> = ArrayList<Activity>()
    private var activityList: ArrayList<Activity> = ArrayList<Activity>()
    private var eventsList: ArrayList<Event> = ArrayList<Event>()
    private var guidesList: ArrayList<Guide> = ArrayList<Guide>()
    private var categoriesList: ArrayList<Category> = ArrayList<Category>()
    private var subCategoriesList: ArrayList<SubCategory> = ArrayList<SubCategory>()
    private var activitiesImagesList = ArrayList<String>()
    private var eventsImagesList: ArrayList<PictureDTO> = ArrayList<PictureDTO>()
    private var guidesImagesList: ArrayList<String> = ArrayList<String>()

    private lateinit var recentSharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var gson = Gson()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        setUpViews()
        setUpListeners()

        return binding.root
    }

    private fun setUpViews(){


        recentSharedPreferences = requireContext()!!.getSharedPreferences("recent", Context.MODE_PRIVATE)
        editor = recentSharedPreferences.edit()

        val json: String = recentSharedPreferences.getString("recentResults", "")!!
        if (json.isEmpty()) {

        } else {
            binding.recentLayout.visibility = View.VISIBLE
            val type = object : TypeToken<List<Activity?>?>() {}.type
            val arrPackageData: List<Activity> = gson.fromJson<List<Activity>>(json, type)
            for (data in arrPackageData) {
                recentList.add(
                    Activity(
                        data.id,
                        data.locationName,
                        "",
                        data.pictures,
                        data.pinActive,
                        "",
                        data.displayCatSubCatName,
                        0F,
                        data.workingHours,
                        data.lat,
                        data.lng
                    )
                )
            }
            activitiesAdapter = ActivitiesResultAdapter(requireContext(), recentList, "recent")
            binding.recentsRecycler.adapter = activitiesAdapter
        }


        binding.searchBar.requestFocus()

        binding.activitiesRecycler.setHasFixedSize(true)
        binding.eventsRecycler.setHasFixedSize(true)
        binding.guidesRecycler.setHasFixedSize(true)
        binding.categoriesChipsRecycler.setHasFixedSize(true)
        binding.subCategoriesChipsRecycler.setHasFixedSize(true)
        binding.recentsRecycler.setHasFixedSize(true)

        categoriesLinearLayoutManager = LinearLayoutManager(context)
        categoriesLinearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.categoriesChipsRecycler.layoutManager = categoriesLinearLayoutManager
        categoriesAdapter = CategoryResultAdapter(requireContext(), categoriesList)
        binding.categoriesChipsRecycler.adapter = categoriesAdapter

        subCategoriesLinearLayoutManager = LinearLayoutManager(context)
        subCategoriesLinearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.subCategoriesChipsRecycler.layoutManager = subCategoriesLinearLayoutManager
        subCategoriesAdapter = SubCategoryResultAdapter(requireContext(), subCategoriesList)
        binding.subCategoriesChipsRecycler.adapter = subCategoriesAdapter

        activitiesLinearLayoutManager = LinearLayoutManager(context)
        activitiesLinearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.activitiesRecycler.layoutManager = activitiesLinearLayoutManager
        activitiesAdapter = ActivitiesResultAdapter(requireContext(), activityList, "search")
        binding.activitiesRecycler.adapter = activitiesAdapter

        eventsLinearLayoutManager = LinearLayoutManager(context)
        eventsLinearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.eventsRecycler.layoutManager = eventsLinearLayoutManager
        eventsAdapter = EventsResultAdapter(requireContext(), eventsList)
        binding.eventsRecycler.adapter = eventsAdapter

        guideLinearLayoutManager = LinearLayoutManager(context)
        guideLinearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.guidesRecycler.layoutManager = guideLinearLayoutManager
        guidesAdapter = GuidesResultAdapter(requireContext(), guidesList)
        binding.guidesRecycler.adapter = guidesAdapter

        recentLinearLayoutManager = LinearLayoutManager(context)
        recentLinearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.recentsRecycler.layoutManager = recentLinearLayoutManager

    }

    private fun setUpListeners(){
        binding.searchBar.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                getSearchResult()
                true
            } else {
                false
            }
        }
    }

    private fun getSearchResult() {

        binding.recentLayout.visibility = View.GONE
        binding.parent.visibility = View.VISIBLE

        val term = binding.searchBar.text.toString()

        getHashKey()

        searchViewModel.getSearch(term)

        lifecycleScope.launchWhenCreated {

            searchViewModel.categoryData.collect { it ->
                if (it.isLoading) {

                    binding.shimmerLayout.stopShimmer()
                    binding.parent.visibility = View.GONE
                    binding.recentLayout.visibility = View.GONE
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

                    categoriesAdapter.setCategoryData(it)

                }
            }
        }

        lifecycleScope.launchWhenCreated {

            searchViewModel.subCategoryData.collect { it ->
                if (it.isLoading) {

                    binding.shimmerLayout.stopShimmer()
                    binding.parent.visibility = View.GONE
                    binding.recentLayout.visibility = View.GONE
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()

                }
                if (it.error.isNotBlank()) {
                    Toast.makeText(requireContext(),it.error, Toast.LENGTH_SHORT).show()
                }
                it.subCategoriesData?.let {

                    binding.shimmerLayout.stopShimmer()
                    binding.parent.visibility = View.VISIBLE
                    binding.shimmerLayout.visibility = View.GONE

                    subCategoriesAdapter.setSubcategoryData(it)

                }
            }
        }

        lifecycleScope.launchWhenCreated {

            searchViewModel.activitiesData.collect { it ->
                if (it.isLoading) {

                    binding.shimmerLayout.stopShimmer()
                    binding.parent.visibility = View.GONE
                    binding.recentLayout.visibility = View.GONE
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()

                }
                if (it.error.isNotBlank()) {
                    binding.activitiesTxt.visibility = View.GONE
                    binding.activitiesRecycler.visibility = View.GONE
                    Toast.makeText(requireContext(),it.error, Toast.LENGTH_SHORT).show()
                }
                it.activitiesData?.let {

                    binding.shimmerLayout.stopShimmer()
                    binding.parent.visibility = View.VISIBLE
                    binding.shimmerLayout.visibility = View.GONE

                    activityList = ArrayList()
                    binding.activitiesTxt.visibility = View.VISIBLE
                    binding.activitiesRecycler.visibility = View.VISIBLE

                    activitiesAdapter.setActivitiesData(it)

                }
            }
        }

        lifecycleScope.launchWhenCreated {

            searchViewModel.guidesData.collect { it ->
                if (it.isLoading) {

                    binding.shimmerLayout.stopShimmer()
                    binding.parent.visibility = View.GONE
                    binding.recentLayout.visibility = View.GONE
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()

                }
                if (it.error.isNotBlank()) {

                    binding.guidesTxt.visibility = View.GONE
                    binding.guidesRecycler.visibility = View.GONE

                    Toast.makeText(requireContext(),it.error, Toast.LENGTH_SHORT).show()
                }
                it.guidesData?.let {

                    binding.shimmerLayout.stopShimmer()
                    binding.parent.visibility = View.VISIBLE
                    binding.shimmerLayout.visibility = View.GONE

                    guidesList = ArrayList()
                    binding.guidesTxt.visibility = View.VISIBLE
                    binding.guidesRecycler.visibility = View.VISIBLE

                    guidesAdapter.setGuidesData(it)

                }
            }
        }

        lifecycleScope.launchWhenCreated {

            searchViewModel.eventsData.collect { it ->
                if (it.isLoading) {

                    binding.shimmerLayout.stopShimmer()
                    binding.parent.visibility = View.GONE
                    binding.recentLayout.visibility = View.GONE
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()

                }
                if (it.error.isNotBlank()) {

                    binding.eventsTxt.visibility = View.GONE
                    binding.eventsRecycler.visibility = View.GONE

                    Toast.makeText(requireContext(),it.error, Toast.LENGTH_SHORT).show()
                }
                it.eventsData?.let {

                    binding.shimmerLayout.stopShimmer()
                    binding.parent.visibility = View.VISIBLE
                    binding.shimmerLayout.visibility = View.GONE

                        eventsList = ArrayList()
                        binding.eventsTxt.visibility = View.VISIBLE
                        binding.eventsRecycler.visibility = View.VISIBLE

                    eventsAdapter.setEventsData(it)

                }
            }
        }
    }

    private fun getHashKey(): String{
        val api_key = "abcdef12345"
        val path = "/api/v2.3/activities/search/"
        return key.md5(path+ key.getRequestTime() +api_key)!!
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString("ARG_PARAM1", param1)
                    putString("ARG_PARAM2", param2)
                }
            }
    }
}