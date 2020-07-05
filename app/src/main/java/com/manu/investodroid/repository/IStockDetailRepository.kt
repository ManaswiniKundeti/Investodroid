package com.manu.investodroid.repository

import com.manu.investodroid.model.FavouriteStock

interface IStockDetailRepository {
    suspend fun getStockDetails(symbol: String)

    suspend fun insertFavouriteStock(symbol: String)
    suspend fun deleteFavouriteStock(symbol: String)
    suspend fun isStockFavorite(symbol: String)
}