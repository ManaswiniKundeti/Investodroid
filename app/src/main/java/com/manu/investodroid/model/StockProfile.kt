package com.manu.investodroid.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class StockProfile (
    @field:Json(name = "companyName") @PrimaryKey val companyName : String,
    @field:Json(name = "price")val price : Float,
    @field:Json(name = "mktCap")val mktCap : Float,
    @field:Json(name = "lastDiv")val lastDiv : Float,
    @field:Json(name = "changes")val changes : Float,
    @field:Json(name = "changesPercentage")val changesPercentage : String,
    @field:Json(name = "exchange")val exchange : String,
    @field:Json(name = "exchangeShortName")val exchangeShortName : String,
    @field:Json(name = "industry")val industry : String,
    @field:Json(name = "website")val website :String,
    @field:Json(name = "description")val description : String,
    @field:Json(name = "eco")val eco : String,
    @field:Json(name = "address")val address : String,
    @field:Json(name = "country")val country : String,
    @field:Json(name = "image") val image : String
)
