package com.example.applicationreachtest.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * @Author: Dilip
 * @Date: 17/10/21
 */
@Parcelize
data class CountryList(val name : Country,var isoCountry: String,var _partition: String,var level2 : List<StateList>):Parcelable {
    override fun toString(): String {
        return name.eng
    }
}