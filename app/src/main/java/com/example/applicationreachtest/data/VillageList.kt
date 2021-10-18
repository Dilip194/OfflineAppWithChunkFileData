package com.example.applicationreachtest.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * @Author: Dilip
 * @Date: 17/10/21
 */

/*
commented landmark as the response is not proper for landmarks
for more details please check address.json file line no 82744 and 135415
*/
@Parcelize
data class VillageList(var name : Village,var villageCode : String/*,var landmarks : List<LandMark>*/) : Parcelable {

    override fun toString(): String {
        return name.eng
    }
}