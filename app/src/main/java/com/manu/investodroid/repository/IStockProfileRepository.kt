package com.manu.investodroid.repository

import com.manu.investodroid.model.FavouriteStock

interface IStockProfileRepository {
    fun getStockProfile(symbol: String)
    fun insertFavouriteStock(favStockSymbol: FavouriteStock)
    fun deleteFavouriteStock(favStockSymbol: FavouriteStock)
}