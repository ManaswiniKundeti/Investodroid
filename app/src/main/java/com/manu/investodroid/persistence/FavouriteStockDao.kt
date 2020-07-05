package com.manu.investodroid.persistence

import androidx.room.*
import com.manu.investodroid.model.FavouriteStock
import com.manu.investodroid.model.Stock

@Dao
interface FavouriteStockDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteStock(favouriteStock : FavouriteStock)

    @Delete
    suspend fun deleteFavouriteStock(favouriteStock : FavouriteStock)

    @Query("SELECT Stock.symbol, Stock.name, Stock.price, Stock.exchange FROM Stock INNER JOIN FavouriteStock ON Stock.symbol = FavouriteStock.favouriteStock")
    suspend fun getFavouriteStocks() : List<Stock>

    @Query("SELECT * from FavouriteStock where favouriteStock=:stockSymbol")
    suspend fun getFavoriteStock(stockSymbol: String): List<FavouriteStock>
}