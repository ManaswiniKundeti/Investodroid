package com.manu.investodroid.repository

interface IStockListRepository {
    fun getStocksList()

    fun getFavouriteStocks()
}