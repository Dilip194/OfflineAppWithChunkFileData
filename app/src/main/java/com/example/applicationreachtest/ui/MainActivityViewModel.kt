package com.example.applicationreachtest.ui

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.applicationreachtest.base.BaseViewModel
import com.example.applicationreachtest.base.Singleton
import com.example.applicationreachtest.data.CountryList
import com.example.applicationreachtest.utils.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.lang.reflect.Type


/**
 * @Author: Dilip
 * @Date: 17/10/21
 */
class MainActivityViewModel : BaseViewModel() {

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    var responseModel : MutableLiveData<List<CountryList>> = MutableLiveData()

    suspend fun fetchDetails(context : Context) : List<CountryList>?{
        var countryList: List<CountryList>? = null
        GlobalScope.async(Dispatchers.IO){
            var jsonFileString = Utility.getJsonFromAsserts(context,"address.json")
            val gson = Gson()
            val listUserType: Type = object : TypeToken<List<CountryList?>?>() {}.getType()
            countryList = gson.fromJson(jsonFileString, listUserType)
        }.await()
        return countryList
    }

    fun fetchAndSet(context: Context){
        GlobalScope.launch(Dispatchers.Main) {
            responseModel.value = fetchDetails(context)
            Singleton.countryList = responseModel.value
        }
    }
    fun onRetrieveListFinish(){
        loadingVisibility.value = View.GONE
    }
}