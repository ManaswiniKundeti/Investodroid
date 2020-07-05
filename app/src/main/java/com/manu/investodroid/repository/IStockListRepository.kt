package com.manu.investodroid.repository

interface IStockListRepository {
    suspend fun getStocksList()

    fun getFavouriteStocks()
}