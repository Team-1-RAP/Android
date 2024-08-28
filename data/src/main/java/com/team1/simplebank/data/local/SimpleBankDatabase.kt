package com.team1.simplebank.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.team1.simplebank.data.local.entity.TransferEntity

@Database(entities =[TransferEntity::class], version = 2, exportSchema = false)
abstract class SimpleBankDatabase:RoomDatabase() {
    //define databaseDao
    abstract fun transferDao():TransferDao
}