package com.manu.investodroid.persistence

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.manu.investodroid.model.Stock

@Dao
interface StockDao {

    @Query("UPDATE Stock SET isFavourite = :isFav WHERE symbol = :symbol")
    fun updateStock(symbol : String, isFav : Boolean)
}