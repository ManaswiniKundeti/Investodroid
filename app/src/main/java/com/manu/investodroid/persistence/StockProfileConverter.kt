package com.manu.investodroid.persistence

import androidx.room.TypeConverter
import com.manu.investodroid.model.StockProfile
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class StockProfileConverter {

    @TypeConverter
    fun fromString(value: String): StockProfile? {
        return getMoshiAdapter().fromJson(value)
    }

    @TypeConverter
    fun fromStockProfile(stockProfile: StockProfile): String {
        return getMoshiAdapter().toJson(stockProfile)
    }

    private fun getMoshiAdapter(): JsonAdapter<StockProfile> {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(StockProfile::class.java)
    }
}