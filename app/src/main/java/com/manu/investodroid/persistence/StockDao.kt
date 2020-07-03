package com.manu.investodroid.persistencex

import androidx.room.*
import com.manu.investodroid.model.Stock
import com.manu.investodroid.model.StockDetail

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStocks(stocks: List<Stock>)

    @Query("SELECT * FROM Stock")
    fun getStockList() : List<Stock>


}