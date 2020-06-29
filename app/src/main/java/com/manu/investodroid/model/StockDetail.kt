package com.manu.investodroid.model

import androidx.room.PrimaryKey
import com.squareup.moshi.Json


data class StockDetail (
    @field:Json(name = "symbol") @PrimaryKey val symbol: String,
    @field:Json(name = "profile") val profile:StockProfile
)

