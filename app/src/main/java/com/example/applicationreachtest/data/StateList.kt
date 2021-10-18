package com.example.applicationreachtest.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * @Author: Dilip
 * @Date: 17/10/21
 */
@Parcelize
data class StateList(var name : State, var level3 : List<DistrictList>) : Parcelable {
    override fun toString(): String {
        return name.eng
    }
}