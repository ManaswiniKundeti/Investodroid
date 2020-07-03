package com.manu.investodroid.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class FavouriteStock (
    @field:Json(name = "symbol") @PrimaryKey val favouriteStock: String
)