package com.example.applicationreachtest.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @Author: Dilip
 * @Date: 17/10/21
 */

@Parcelize
data class LandMark(var eng : String) : Parcelable {

    override fun toString(): String {
        return eng
    }
}