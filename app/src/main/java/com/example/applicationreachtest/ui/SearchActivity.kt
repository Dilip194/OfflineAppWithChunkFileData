package com.example.applicationreachtest.ui

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.applicationreachtest.R
import com.example.applicationreachtest.base.Singleton
import com.example.applicationreachtest.data.CountryList
import com.example.applicationreachtest.databinding.ActivitySearchBinding
import com.example.applicationreachtest.utils.Constants
import com.example.applicationreachtest.utils.Constants.Companion.COUNTRY_LIST
import com.example.applicationreachtest.utils.Constants.Companion.DISTRICT_LIST
import com.example.applicationreachtest.utils.Constants.Companion.POSITION
import com.example.applicationreachtest.utils.Constants.Companion.REQUEST_TYPE
import com.example.applicationreachtest.utils.Constants.Companion.STATE_LIST
import com.example.applicationreachtest.utils.Constants.Companion.TALUK_LIST
import com.example.applicationreachtest.utils.Constants.Companion.VILLAGE_LIST

class SearchActivity : AppCompatActivity(),AdapterView.OnItemClickListener {

    private var adapter : ArrayAdapter<*>? = null
    private var countryList: List<CountryList>? = null
    private lateinit var dataType : String
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchActivityViewModel
    var requestType = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        viewModel = ViewModelProviders.of(this).get(SearchActivityViewModel::class.java)
        supportActionBar?.title = ""
        this.setFinishOnTouchOutside(false)

        countryList = Singleton.countryList
        requestType = intent.getIntExtra(REQUEST_TYPE,0)

        setAdapter(requestType)

        binding.listView.setOnItemClickListener(this)

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
            }
        }
    }

    private fun setAdapter(requestType : Int){
        when (requestType) {
            COUNTRY_LIST ->{
                adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,countryList!!)
            }
            STATE_LIST ->{
                adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,Singleton.stateList!!)
            }
            DISTRICT_LIST -> {
                adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,Singleton.districtList!!)
            }
            TALUK_LIST ->{
                adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,Singleton.talukList!!)
            }
            VILLAGE_LIST ->{
                adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,Singleton.villageList!!)
            }
        }
        binding.listView.adapter = adapter
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)

        val search = menu?.findItem(R.id.search)
        val searchView = search?.actionView as SearchView
        //searchView.queryHint = "Search... "
        setSearchHint(requestType,searchView)

        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return true
            }
        } )

        return super.onCreateOptionsMenu(menu)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(this,parent?.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show()
        val intent = Intent()
        intent.putExtra(POSITION,position)
        intent.putExtra(REQUEST_TYPE,requestType)
        setResult(RESULT_OK,intent)
        finish()
    }

    private fun setSearchHint(requestType: Int,searchView: SearchView){
        when (requestType) {
            COUNTRY_LIST ->{
                searchView.queryHint = "Search " + "Country"
            }
            STATE_LIST ->{
                searchView.queryHint = "Search " + "State"
            }
            DISTRICT_LIST -> {
                searchView.queryHint = "Search " + "District"
            }
            TALUK_LIST ->{
                searchView.queryHint = "Search " + "Taluk"
            }
            VILLAGE_LIST ->{
                searchView.queryHint = "Search " + "Village"
            }
        }
    }
}