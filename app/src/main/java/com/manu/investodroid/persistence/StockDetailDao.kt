package com.manu.investodroid.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manu.investodroid.model.Stock
import com.manu.investodroid.model.StockDetail

@Dao
interface StockDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStockDetails(stockDetails: StockDetail)

    @Query("SELECT * FROM StockDetail WHERE symbol = :inputSymbol")
    fun getStockDetails(inputSymbol : String) : StockDetail

}