package com.manu.investodroid.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Stock (
    @field:Json(name = "symbol") @PrimaryKey val symbol: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "price") val price : Float,
    @field:Json(name = "exchange") val exchange: String
)