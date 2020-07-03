package com.manu.investodroid.persistencex

import androidx.room.*
import com.manu.investodroid.model.Stock

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStocks(stocks: List<Stock>)

//    @Query("UPDATE Stock SET isFavourite = :isFav WHERE symbol = :symbol")
//    fun updateStock(symbol: String, isFav: Boolean?)
//
//    @Query("SELECT * FROM Stock WHERE isFavourite = :isFav")
//    fun getfavStock(isFav : Boolean) : List<Stock>
}