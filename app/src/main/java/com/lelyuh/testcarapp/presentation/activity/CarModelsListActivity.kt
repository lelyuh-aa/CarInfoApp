package com.lelyuh.testcarapp.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.transition.TransitionManager
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
 * Activity for represent list of car models of given manufacturer
 *
 * @author Leliukh Aleksandr
 */
class CarModelsListActivity : AppCompatActivity(), CarsSimlpeDataListAdapter.OnItemClickListener {

    @Inject
    internal lateinit var viewModelFactory: CarViewModelFactory
    private lateinit var listAdapter: CarsSimlpeDataListAdapter
    private lateinit var binding: CommonListActivityBinding

    private val viewModel: CarViewModel by viewModels { viewModelFactory }

    private var carModelList: List<String>? = null
    private lateinit var carModel: CarModel

    private var filterMask = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as CarApplication).carComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = CommonListActivityBinding.inflate(layoutInflater)
        carModel = intent.getSerializableExtra(CAR_MODEL_KEY) as CarModel
        setContentView(binding.root)
        initViews()
        initToolbar()
        initAdapter()
        observeViewModel()
        loadCarTypes()
    }

    override fun onBackPressed() {
        if (binding.searchView.isVisible) {
            setSearchVisibility(false)
        } else {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(FILTER_MASK_KEY, filterMask)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val storedFilteredMask = savedInstanceState.getString(FILTER_MASK_KEY)
        if (storedFilteredMask.isNullOrBlank()) {
            setSearchVisibility(false)
        } else {
            filterMask = storedFilteredMask
            setSearchVisibility(true)
            binding.searchView.setQuery(filterMask, false)
        }
    }

    override fun onItemClick(data: String) {
        carModel.carModelType = data
        startActivity(CarBuildDatesListActivity.newIntent(this, carModel))
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.titleTextView.text = carModel.manufacturerName
    }

    private fun initViews() {
        with(binding) {
            searchIconView.setOnClickListener {
                setSearchVisibility(true)
            }
            searchView.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?) = false

                    override fun onQueryTextChange(newText: String?): Boolean {
                        TransitionManager.beginDelayedTransition(binding.root)
                        filterMask = newText.orEmpty()
                        updateList()
                        return false
                    }
                }
            )
        }
    }

    private fun initAdapter() {
        listAdapter = CarsSimlpeDataListAdapter(this)
        binding.recyclerView.adapter = listAdapter
    }

    private fun observeViewModel() {
        viewModel.carTypesLiveData.observe(this) { carTypesList ->
            Log.d("CarModelsListActivity", carTypesList.toString())
            carModelList = ArrayList(carTypesList)
            updateList()
            binding.searchIconView.isVisible = carTypesList.isNotEmpty()
            binding.progress.isVisible = false
        }
        viewModel.errorLiveData.observe(this) {
            binding.progress.isVisible = false
            showErrorDialog(this)
        }
    }

    private fun updateList() {
        val filteredList = carModelList?.filter { carModel ->
            carModel.lowercase().contains(filterMask.lowercase())
        }
        listAdapter.updateListData(
            filteredList.takeIf { it.isNullOrEmpty().not() }
                ?: listOf(getString(R.string.search_empty_result))
        )
    }

    private fun loadCarTypes() {
        viewModel.loadCarTypes(carModel.manufacturerId)
    }

    private fun setSearchVisibility(isShowSearch: Boolean) {
        with(binding) {
            titleTextView.isVisible = !isShowSearch
            searchIconView.isVisible = !isShowSearch
            searchView.isVisible = isShowSearch
            searchView.isIconified = !isShowSearch
            if (!isShowSearch) {
                filterMask = ""
            }
        }
    }

    companion object {
        private const val CAR_MODEL_KEY = "carModelKey"
        private const val FILTER_MASK_KEY = "filterMaskKey"

        fun newIntent(context: Context, model: CarModel) =
            Intent(context, CarModelsListActivity::class.java).apply {
                putExtra(CAR_MODEL_KEY, model)
            }
    }
}
