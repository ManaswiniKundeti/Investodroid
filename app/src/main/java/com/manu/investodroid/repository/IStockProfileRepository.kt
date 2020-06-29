package com.manu.investodroid.repository

interface IStockProfileRepository {
    fun getStockProfile(symbol : String)
}