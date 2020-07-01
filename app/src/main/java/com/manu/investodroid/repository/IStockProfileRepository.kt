package com.manu.investodroid.repository

interface IStockProfileRepository {
    fun getStockProfile(symbol: String)
    fun updateStock(symbol: String)
}