package com.baxramov.presentation.screens

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.baxramov.R
import com.baxramov.databinding.ActivityMainBinding
import com.baxramov.presentation.adapter.WeatherForecastAdapter
import com.baxramov.presentation.view_model.MyViewModelFactory
import com.baxramov.presentation.view_model.WeatherForecastViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

        setupRecyclerView()
        searchForCity(binding.searchViewLocation)
        observeWeatherForecastsListLD()
    }

    private fun searchForCity(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    coroutineScope.launch {
                        viewModel.getWeatherForecast(query, FORECAST_LENGTH)
                    }
                    binding.progressBar.visibility = ProgressBar.VISIBLE
                    binding.tvCityName.text = query.uppercase()

                } else {
                    showToast(getString(R.string.enter_city_name_warning))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun observeWeatherForecastsListLD() {
        viewModel.lvWeatherForecastList.observe(this) {
            weatherForecastAdapter.submitList(it)
            binding.progressBar.visibility = ProgressBar.INVISIBLE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

    companion object {
        private const val FORECAST_LENGTH = 5
    }
}