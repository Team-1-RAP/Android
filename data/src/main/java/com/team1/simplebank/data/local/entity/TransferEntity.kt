package com.team1.simplebank.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TransferEntity")
data class TransferEntity(

    @ColumnInfo("userName")
    val userName:String,
    @ColumnInfo("fullName")
    val fullName: String,
    @ColumnInfo("bankName")
    val bankName: String,
    @ColumnInfo("bankId")
    val bankId: Int,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("noAccount")
    val noAccount: String,
    @ColumnInfo("adminFee")
    val adminFee:Int
)
