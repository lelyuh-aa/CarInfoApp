package com.lelyuh.testcarapp.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.lelyuh.testcarapp.CarApplication
import com.lelyuh.testcarapp.R
import com.lelyuh.testcarapp.databinding.CommonListActivityBinding
import com.lelyuh.testcarapp.models.presentation.CarModel
import com.lelyuh.testcarapp.presentation.adapter.CarsListAdapter
import com.lelyuh.testcarapp.presentation.error.ErrorHelper.showErrorDialog
import com.lelyuh.testcarapp.presentation.viewmodel.CarViewModel
import com.lelyuh.testcarapp.presentation.viewmodel.CarViewModelFactory
import javax.inject.Inject

/**
 * Activity for represent list of car manufacturers
 *
 * @author Leliukh Aleksandr
 */
class ManufacturerListActivity : AppCompatActivity(), CarsListAdapter.OnItemHanlderListener {

    @Inject
    internal lateinit var viewModelFactory: CarViewModelFactory
    private lateinit var listAdapter: CarsListAdapter
    private lateinit var binding: CommonListActivityBinding

    private val viewModel: CarViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as CarApplication).carComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = CommonListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        initAdapter()
        observeViewModel()
        viewModel.loadManufacturers()
    }

    override fun onNextPageLoad() {
        binding.progress.isVisible = true
        viewModel.loadManufacturers()
    }

    override fun onItemClick(model: CarModel) {
        startActivity(CarModelsListActivity.newIntent(this, model))
    }

    private fun initToolbar() {
        binding.titleTextView.text = getString(R.string.manufacturer_title)
    }

    private fun initAdapter() {
        listAdapter = CarsListAdapter(this)
        binding.recyclerView.adapter = listAdapter
    }

    private fun observeViewModel() {
        viewModel.manufacturerLiveData.observe(this) { manufacturerList ->
            Log.d("ManufacturerActivity", manufacturerList.toString())
            listAdapter.updateListData(manufacturerList)
            binding.progress.isVisible = false
        }
        viewModel.errorLiveData.observe(this) {
            binding.progress.isVisible = false
            showErrorDialog(this, R.string.error_close_button)
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, ManufacturerListActivity::class.java)
    }
}
