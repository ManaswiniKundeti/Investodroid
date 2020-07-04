package com.manu.investodroid.repository

import com.manu.investodroid.model.FavouriteStock

interface IStockDetailRepository {
    fun getStockProfile(symbol: String)


    fun insertFavouriteStock(symbol: String)
    fun deleteFavouriteStock(symbol: String)
    fun isStockFavorite(symbol: String)
}