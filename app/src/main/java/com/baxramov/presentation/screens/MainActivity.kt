package com.baxramov.presentation.screens

import android.os.Bundle
import android.view.Gravity
import android.view.View
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

    private var isInternetAvailable = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        changeTvVisibilityDependingOnRcv(binding)
        setupRecyclerView()
        searchForCity(binding.searchViewLocation)
        observeWeatherForecastsList()
        observeErrors(savedInstanceState)
        binding.tvCityNamePresenter.text = viewModel.getCityName()
    }

    private fun searchForCity(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query?.trim()?.isNotEmpty() == true) {
                    coroutineScope.launch {
                        delay(300)
                        viewModel.getWeatherForecast(query, FORECAST_LENGTH)
                        viewModel.setCityName(query.uppercase())
                    }
                    changeViewsVisibility(
                        tvCityNamePresenterVisible = true,
                        progressBarLoadingVisible = true
                    )
                    binding.tvCityNamePresenter.text = query.trim().uppercase()
                } else {
                    changeViewsVisibility(tvEnterCityNameVisible = true)
                    showToast(getString(R.string.enter_city_name_warning))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() == true) {
                    changeViewsVisibility(tvEnterCityNameVisible = true)
                } else {
                    binding.tvEnterCityName.visibility = TextView.INVISIBLE
                }
                return true
            }
        })
    }

    private fun observeWeatherForecastsList() {
        viewModel.lvWeatherForecastList.observe(this) {
            weatherForecastAdapter.submitList(it)
            changeViewsVisibility(tvCityNamePresenterVisible = true, rcvForecastListVisible = true)
        }
    }

    private fun observeErrors(savedInstanceState: Bundle?) {
        viewModel.lvNoInternetError.observe(this) {
            if (getInternetState(savedInstanceState)) {
                binding.tvError.gravity = Gravity.CENTER
                binding.tvError.text =
                    getString(R.string.please_check_your_internet_connection_and_try_again)
                changeViewsVisibility(tvErrorVisible = true, imgNoInternetVisible = true)
                isInternetAvailable = false
            }
        }

        viewModel.lvIncorrectCityNameError.observe(this) {
            binding.tvError.gravity = Gravity.CENTER_HORIZONTAL
            binding.tvError.text = getString(R.string.check_city_name)
            changeViewsVisibility(tvErrorVisible = true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(INTERNET_STATE, isInternetAvailable)
        super.onSaveInstanceState(outState)
    }

    private fun getInternetState(savedInstanceState: Bundle?): Boolean =
        savedInstanceState?.getBoolean(INTERNET_STATE) ?: isInternetAvailable

    private fun setupRecyclerView() {
        val recyclerView = binding.rcvForecastList
        recyclerView.adapter = weatherForecastAdapter
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun changeTvVisibilityDependingOnRcv(binding: ActivityMainBinding) {
        if (binding.rcvForecastList.visibility == RecyclerView.VISIBLE) {
            binding.tvEnterCityName.visibility = TextView.INVISIBLE
        } else {
            binding.tvEnterCityName.visibility = TextView.VISIBLE
        }
    }

    private fun changeViewsVisibility(
        tvErrorVisible: Boolean = false,
        tvCityNamePresenterVisible: Boolean = false,
        tvEnterCityNameVisible: Boolean = false,
        imgNoInternetVisible: Boolean = false,
        rcvForecastListVisible: Boolean = false,
        progressBarLoadingVisible: Boolean = false
    ) {
        with(binding) {
            tvError.visibility = getViewVisibility(tvErrorVisible)
            tvCityNamePresenter.visibility = getViewVisibility(tvCityNamePresenterVisible)
            tvEnterCityName.visibility = getViewVisibility(tvEnterCityNameVisible)
            imageViewNoInternetConnection.visibility = getViewVisibility(imgNoInternetVisible)
            rcvForecastList.visibility = getViewVisibility(rcvForecastListVisible)
            progressBarLoading.visibility = getViewVisibility(progressBarLoadingVisible)
        }
    }

    private fun getViewVisibility(visibility: Boolean): Int =
        if (visibility) View.VISIBLE else View.INVISIBLE


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val FORECAST_LENGTH = "5"
        private const val INTERNET_STATE = "is_internet_available"
    }
}