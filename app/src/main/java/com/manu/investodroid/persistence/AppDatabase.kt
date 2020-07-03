package com.manu.investodroid.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.manu.investodroid.model.FavouriteStock
import com.manu.investodroid.model.Stock
import com.manu.investodroid.persistencex.StockDao
import com.manu.investodroid.repository.StockListRepository

@Database(entities = [Stock::class, FavouriteStock::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao() : StockDao
    abstract fun favStockDao() : FavouriteStockDao

    companion object {
        var INSTANCE : AppDatabase? = null

        fun getAppDatabase(context : Context) : AppDatabase? {
            if(INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,"myDB")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase(){
            INSTANCE = null
        }
    }
}