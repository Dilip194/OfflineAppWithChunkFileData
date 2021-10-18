package com.example.applicationreachtest.utils

import android.content.Context
import java.nio.charset.Charset

/**
 * @Author: Dilip
 * @Date: 17/10/21
 */
class Utility {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object{

        fun getJsonFromAsserts(context: Context, fileName : String) : String?{
             var jsonString : String

             try {
                 val inputStream = context.assets.open(fileName)
                 val size = inputStream.available()
                 val buffer = ByteArray(size)
                 inputStream.read(buffer)
                 inputStream.close()

                 val charset: Charset = Charsets.UTF_8
                 jsonString = String(buffer, charset)
             }catch (exception : Exception){
                 exception.printStackTrace()
                 return null
             }
            return jsonString
        }
    }
}