package com.manu.investodroid.helpers

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper(context : Context) {

    private val sharedPreferenceFile = "mySharedPreferenceFile"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(sharedPreferenceFile,Context.MODE_PRIVATE)
    private val editor:SharedPreferences.Editor =  sharedPreferences.edit()

    fun putLong(key: String, value: Long) {
        editor.putLong("network_call_time_stamp", System.currentTimeMillis() / 1000)
        editor.apply()
    }

    fun getLong(key: String, defaultValue: Long) = sharedPreferences.getLong(key, defaultValue)
}