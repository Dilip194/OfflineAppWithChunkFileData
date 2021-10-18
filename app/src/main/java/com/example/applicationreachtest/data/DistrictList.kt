package com.example.applicationreachtest.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * @Author: Dilip
 * @Date: 17/10/21
 */

@Parcelize
data class DistrictList(var name : District,var level4 : List<TalukList>) :Parcelable {
    override fun toString(): String {
        return name.eng
    }
}