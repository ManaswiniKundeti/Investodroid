package com.manu.investodroid.repository

import com.manu.investodroid.model.FavouriteStock

interface IStockDetailRepository {
    fun getStockProfile(symbol: String)


    fun insertFavouriteStock(symbol: FavouriteStock)
    fun deleteFavouriteStock(symbol: FavouriteStock)
}