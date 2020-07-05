package com.manu.investodroid.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manu.investodroid.model.Stock

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStocks(stocks: List<Stock>)

    @Query("SELECT * FROM Stock")
    suspend fun getStockList() : List<Stock>

}