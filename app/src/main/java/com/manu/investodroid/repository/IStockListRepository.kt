package com.manu.investodroid.repository

interface IStockListRepository {
    suspend fun getStocksList()

    suspend fun getFavouriteStocks()
}