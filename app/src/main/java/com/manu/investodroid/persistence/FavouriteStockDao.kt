package com.manu.investodroid.persistence

import androidx.room.*
import com.manu.investodroid.model.FavouriteStock
import com.manu.investodroid.model.Stock

@Dao
interface FavouriteStockDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavouriteStock(favouriteStock : FavouriteStock)

    @Delete
    fun deleteFavouriteStock(favouriteStock : FavouriteStock)

    @Query("SELECT Stock.symbol, Stock.name, Stock.price, Stock.exchange FROM Stock INNER JOIN FavouriteStock ON Stock.symbol = FavouriteStock.favouriteStock")
    fun getFavouriteStocks() : List<Stock>

    @Query("SELECT * from FavouriteStock where favouriteStock=:stockSymbol")
    fun getFavoriteStock(stockSymbol: String): List<FavouriteStock>
}