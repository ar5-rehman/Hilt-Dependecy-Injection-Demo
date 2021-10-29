package com.oui.parisiproject.presentation.discovery

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.oui.parisiproject.R
import com.oui.parisiproject.databinding.FragmentWeatherDetailsBinding
import com.oui.parisiproject.domain.model.discoveryModels.Weather
import kotlin.collections.ArrayList

class WeatherDetailsFragment : DialogFragment() {

    private lateinit var binding: FragmentWeatherDetailsBinding
    private lateinit var weatherIcon: String
    private var time: String = ""
    private var currentDate: String = ""
    private var weatherTemperature: Int = 0
    private lateinit var weatherList: ArrayList<Weather>
    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)

        layoutManager = GridLayoutManager(context, 2)
        binding.weatherHourRecyclerView.layoutManager = layoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("discoveryDataKey", this) { key, bundle ->

            weatherIcon = bundle.getString("icon").toString()
            weatherList = bundle.getParcelableArrayList("list")!!
            time = bundle.getString("time").toString()
            currentDate = bundle.getString("date").toString()
            weatherTemperature = bundle.getInt("weatherTemperature")

            binding.date.setText(currentDate)
            binding.hour.setText(time)
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
            weatherAdapter = WeatherAdapter(weatherList)
            binding.weatherHourRecyclerView.adapter = weatherAdapter

        }
    }

    override fun getTheme(): Int {
        return R.style.CustomDialog
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString("ARG_PARAM1", param1)
                    putString("ARG_PARAM2", param2)
                }
            }
    }
}