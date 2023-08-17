package com.baxramov.presentation.screens

import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baxramov.R
import com.baxramov.databinding.ActivityMainBinding
import com.baxramov.presentation.adapter.WeatherForecastAdapter
import com.baxramov.presentation.view_model.MyViewModelFactory
import com.baxramov.presentation.view_model.WeatherForecastViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val viewModelFactory by lazy {
        MyViewModelFactory(application)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[WeatherForecastViewModel::class.java]
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val weatherForecastAdapter = WeatherForecastAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        changeTvVisibilityDependingOnRcv(binding)
        setupRecyclerView()
        searchForCity(binding.searchViewLocation)
        observeWeatherForecastsList()
        observeInternetError()
        binding.tvCityName.text = viewModel.getCityName()
    }

    private fun searchForCity(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                val progressBarVisibility: Int
                if (query != null && query.trim().isNotEmpty()) {
                    coroutineScope.launch {
                        delay(300)
                        viewModel.getWeatherForecast(query, FORECAST_LENGTH)
                        viewModel.setCityName(query.uppercase())
                    }
                    makeRecyclerViewVisible()
                    progressBarVisibility = ProgressBar.VISIBLE
                    binding.tvCityName.text = query.trim().uppercase()
                } else {
                    makeRecyclerViewInvisible()
                    progressBarVisibility = ProgressBar.INVISIBLE
                    showToast(getString(R.string.enter_city_name_warning))
                }
                binding.progressBar.visibility = progressBarVisibility
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() == true) {
                    hideCheckInternetStatus()
                    changeTvVisibilityDependingOnRcv(binding)
                } else {
                    binding.tvEnterCityName.visibility = TextView.INVISIBLE
                }
                return false
            }
        })
    }

    private fun observeWeatherForecastsList() {
        viewModel.lvWeatherForecastList.observe(this) {
            weatherForecastAdapter.submitList(it)
            makeRecyclerViewVisible()
        }


    }

    private fun observeInternetError() {
        viewModel.lvError.observe(this) {
            val message: String
            if (it == INTERNET_ERROR && weatherForecastAdapter.currentList.isEmpty()) {
                message =
                    getString(R.string.please_check_your_internet_connection_and_try_again)
                showCheckInternetStatus()
            } else {
                showCheckInternetStatus()
                message = getString(R.string.check_city_name)
            }
            binding.tvCheckInternetConnection.text = message
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rcvWeatherInfoList
        recyclerView.adapter = weatherForecastAdapter
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun changeTvVisibilityDependingOnRcv(binding: ActivityMainBinding) {
        if (binding.rcvWeatherInfoList.visibility == RecyclerView.VISIBLE) {
            binding.tvEnterCityName.visibility = TextView.INVISIBLE
        } else {
            binding.tvEnterCityName.visibility = TextView.VISIBLE
        }
    }

    private fun makeRecyclerViewVisible() {
        binding.rcvWeatherInfoList.visibility = RecyclerView.VISIBLE
        binding.tvCityName.visibility = TextView.VISIBLE

        binding.progressBar.visibility = ProgressBar.INVISIBLE
        binding.tvEnterCityName.visibility = TextView.INVISIBLE
        binding.imageViewNoInternetConnection.visibility = ImageView.INVISIBLE
        binding.tvCheckInternetConnection.visibility = TextView.INVISIBLE
    }

    private fun makeRecyclerViewInvisible() {
        binding.tvCityName.visibility = TextView.INVISIBLE
        binding.rcvWeatherInfoList.visibility = RecyclerView.INVISIBLE

        binding.progressBar.visibility = ProgressBar.VISIBLE
    }

    private fun showCheckInternetStatus() {
        binding.imageViewNoInternetConnection.visibility = ImageView.VISIBLE
        binding.tvCheckInternetConnection.visibility = TextView.VISIBLE

        binding.progressBar.visibility = ProgressBar.INVISIBLE
        binding.rcvWeatherInfoList.visibility = RecyclerView.INVISIBLE
        binding.tvCityName.visibility = TextView.INVISIBLE
        binding.tvEnterCityName.visibility = TextView.INVISIBLE

    }

    private fun hideCheckInternetStatus() {
        binding.imageViewNoInternetConnection.visibility = ImageView.INVISIBLE
        binding.tvCheckInternetConnection.visibility = TextView.INVISIBLE

        binding.tvEnterCityName.visibility = TextView.VISIBLE

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val FORECAST_LENGTH = "5"
        const val INTERNET_ERROR = "Check internet connection"
    }
}