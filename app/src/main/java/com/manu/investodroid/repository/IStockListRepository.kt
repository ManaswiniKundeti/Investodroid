package com.manu.investodroid.repository

import com.manu.investodroid.model.Stock

interface IStockListRepository {
    suspend fun getStocksList(): List<Stock>?

    suspend fun getFavouriteStocks(): List<Stock>?
}