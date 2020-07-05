package com.manu.investodroid.model

import com.squareup.moshi.Json

data class StockProfile (
    @field:Json(name = "companyName") val companyName : String,
    @field:Json(name = "price") val price : Float,
    @field:Json(name = "mktCap")val mktCap : Float,
    @field:Json(name = "lastDiv")val lastDiv : Float,
    @field:Json(name = "changes")val changes : Float,
    @field:Json(name = "changesPercentage")val changesPercentage : String,
    @field:Json(name = "exchange")val exchange : String,
    @field:Json(name = "exchangeShortName")val exchangeShortName : String,
    @field:Json(name = "industry")val industry : String,
    @field:Json(name = "website")val website :String,
    @field:Json(name = "description")val description : String,
    @field:Json(name = "ceo")val ceo : String,
    @field:Json(name = "country")val country : String,
    @field:Json(name = "fullTimeEmployees")val fullTimeEmployees : String?,
    @field:Json(name = "address")val address : String,
    @field:Json(name = "city")val city : String?,
    @field:Json(name = "state")val state : String?,
    @field:Json(name = "image") val image : String
)
