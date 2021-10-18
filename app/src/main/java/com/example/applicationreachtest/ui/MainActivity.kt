package com.example.applicationreachtest.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicationreachtest.R
import com.example.applicationreachtest.base.Singleton
import com.example.applicationreachtest.data.CountryList
import com.example.applicationreachtest.databinding.ActivityMainBinding
import com.example.applicationreachtest.utils.Constants.Companion.COUNTRY
import com.example.applicationreachtest.utils.Constants.Companion.COUNTRY_LIST
import com.example.applicationreachtest.utils.Constants.Companion.DISTRICT_LIST
import com.example.applicationreachtest.utils.Constants.Companion.POSITION
import com.example.applicationreachtest.utils.Constants.Companion.REQUEST_TYPE
import com.example.applicationreachtest.utils.Constants.Companion.STATE_LIST
import com.example.applicationreachtest.utils.Constants.Companion.TALUK_LIST
import com.example.applicationreachtest.utils.Constants.Companion.VILLAGE_LIST
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private var errorSnackbar: Snackbar? = null
    private lateinit var countryList: ArrayList<CountryList>
    var countryPos: Int = 0
    var statePos: Int = 0
    var districtPos: Int = 0
    var talukPos: Int = 0
    var villagePos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.fetchAndSet(this)

        viewModel.responseModel.observe(this, {
            countryList = it as ArrayList<CountryList>
            viewModel.onRetrieveListFinish()
        })

        binding.spinnerCountry.setOnClickListener(this)
        binding.spinnerState.setOnClickListener(this)
        binding.spinnerDisc.setOnClickListener(this)
        binding.spinnerTaluk.setOnClickListener(this)
        binding.spinnerVillage.setOnClickListener(this)
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.ok, View.OnClickListener { errorSnackbar?.dismiss() })
        errorSnackbar?.show()
    }

    override fun onClick(v: View?) {
        var id = v?.id
        when (id) {
            R.id.spinner_country ->
                openActivityForResult(COUNTRY_LIST, 0)
            R.id.spinner_state -> {
                if (Singleton.stateList != null) {
                    openActivityForResult(STATE_LIST, statePos)
                } else {
                    showError(R.string.select_country_error)
                }
            }
            R.id.spinner_disc -> {
                if (Singleton.districtList != null) {
                    openActivityForResult(DISTRICT_LIST, districtPos)
                } else {
                    showError(R.string.select_state_error)
                }
            }
            R.id.spinner_taluk -> {
                if (Singleton.talukList != null){
                openActivityForResult(TALUK_LIST, talukPos)
                }else{
                    showError(R.string.select_district)
                }
            }
            R.id.spinner_village -> {
                if (Singleton.villageList != null){
                openActivityForResult(VILLAGE_LIST, villagePos)
                }else{
                    showError(R.string.select_taluk)
                }
            }

        }
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val requestType = data?.getIntExtra(REQUEST_TYPE, 0)
                val position = data?.getIntExtra(POSITION, 0)
                doOperations(requestType, position)
            }
        }

    private fun doOperations(requestType: Int?, position: Int?) {
        when (requestType) {
            COUNTRY_LIST -> {
                countryPos = position!!
                Singleton.stateList = countryList.get(countryPos).level2
                binding.spinnerCountry.text = countryList.get(countryPos).name.eng
            }
            STATE_LIST -> {
                statePos = position!!
                Singleton.districtList = countryList.get(countryPos).level2.get(statePos).level3
                binding.spinnerState.text =
                    countryList.get(countryPos).level2.get(statePos).name.eng
            }
            DISTRICT_LIST -> {
                districtPos = position!!
                Singleton.talukList =
                    countryList.get(countryPos).level2.get(statePos).level3.get(districtPos).level4
                binding.spinnerDisc.text =
                    countryList.get(countryPos).level2.get(statePos).level3.get(districtPos).name.eng
            }
            TALUK_LIST -> {
                talukPos = position!!
                Singleton.villageList =
                    countryList.get(countryPos).level2.get(statePos).level3.get(districtPos).level4.get(
                        talukPos
                    ).level5
                binding.spinnerTaluk.text =
                    countryList.get(countryPos).level2.get(statePos).level3.get(districtPos).level4.get(
                        talukPos
                    ).name.eng
            }
            VILLAGE_LIST -> {
                villagePos = position!!
                binding.spinnerVillage.text =
                    countryList.get(countryPos).level2.get(statePos).level3.get(districtPos).level4.get(
                        talukPos
                    ).level5.get(villagePos).name.eng
            }
        }
    }

    fun openActivityForResult(requestType: Int, position: Int) {
        val intent = Intent(this, SearchActivity::class.java)
        intent.putExtra(REQUEST_TYPE, requestType)
        intent.putExtra(POSITION, position)
        resultLauncher.launch(intent)
    }
}