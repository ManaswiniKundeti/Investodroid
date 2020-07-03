package com.manu.investodroid.persistencex

import androidx.room.*
import com.manu.investodroid.model.Stock

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStocks(stocks: List<Stock>)
}