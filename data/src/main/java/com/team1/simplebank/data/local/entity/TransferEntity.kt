package com.team1.simplebank.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TransferEntity")
data class TransferEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int?=null,
    @ColumnInfo("fullName")
    val fullName: String,
    @ColumnInfo("bankName")
    val bankName: String,
    @ColumnInfo("bankId")
    val bankId: String,
    @ColumnInfo("noAccount")
    val noAccount: String,
)
