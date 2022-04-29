package com.lelyuh.testcarapp.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.lelyuh.testcarapp.CarApplication
import com.lelyuh.testcarapp.R
import com.lelyuh.testcarapp.databinding.CommonListActivityBinding
import com.lelyuh.testcarapp.models.presentation.CarModel
import com.lelyuh.testcarapp.presentation.adapter.CarsSimlpeDataListAdapter
import com.lelyuh.testcarapp.presentation.error.ErrorHelper.showErrorDialog
import com.lelyuh.testcarapp.presentation.viewmodel.CarViewModel
import com.lelyuh.testcarapp.presentation.viewmodel.CarViewModelFactory
import javax.inject.Inject

/**
 * Activity for represent list of build years of given car model
 *
 * @author Leliukh Aleksandr
 */
class CarBuildDatesListActivity : AppCompatActivity(), CarsSimlpeDataListAdapter.OnItemClickListener {

    @Inject
    internal lateinit var viewModelFactory: CarViewModelFactory
    private lateinit var listAdapter: CarsSimlpeDataListAdapter
    private lateinit var binding: CommonListActivityBinding

    private val viewModel: CarViewModel by viewModels { viewModelFactory }

    private lateinit var carModel: CarModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as CarApplication).carComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = CommonListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        carModel = intent.getSerializableExtra(CAR_MODEL_KEY) as CarModel
        initToolbar()
        initAdapter()
        observeViewModel()
        loadCarTypes()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(data: String) {
        carModel.carBuildDate = data
        startActivity(CarSummaryActivity.newIntent(this, carModel))
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.titleTextView.text =
            getString(R.string.car_build_dates_title_format, carModel.manufacturerName, carModel.carModelType)
    }

    private fun initAdapter() {
        listAdapter = CarsSimlpeDataListAdapter(this)
        binding.recyclerView.adapter = listAdapter
    }

    private fun observeViewModel() {
        viewModel.carBuildDatesLiveData.observe(this) { carBuildDatesList ->
            Log.d("CarBuildDatesListActivity", carBuildDatesList.toString())
            listAdapter.updateListData(carBuildDatesList)
            binding.progress.isVisible = false
        }
        viewModel.errorLiveData.observe(this) {
            binding.progress.isVisible = false
            showErrorDialog(this)
        }
    }

    private fun loadCarTypes() {
        viewModel.loadCarModelBuildDates(carModel.manufacturerId, carModel.carModelType)
    }

    companion object {
        private const val CAR_MODEL_KEY = "carModelKey"

        fun newIntent(context: Context, model: CarModel) =
            Intent(context, CarBuildDatesListActivity::class.java).apply {
                putExtra(CAR_MODEL_KEY, model)
            }
    }
}
