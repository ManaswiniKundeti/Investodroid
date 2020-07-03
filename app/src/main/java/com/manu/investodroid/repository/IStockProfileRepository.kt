package com.manu.investodroid.repository

interface IStockProfileRepository {
    fun getStockProfile(symbol: String)
    fun updateStockInDB(symbol: String)
    fun unFavouriteStock(symbol : String)
}