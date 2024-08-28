package com.team1.simplebank.data.local

import androidx.room.Database
import com.team1.simplebank.data.local.entity.TransferEntity

@Database(entities =[TransferEntity::class], version = 1, exportSchema = false)
abstract class SimpleBankDatabase {

    //define databaseDao
    abstract fun transferDao():TransferDao
}