//package com.team1.simplebank.data.local
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.team1.simplebank.data.local.entity.TransferEntity
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface TransferDao {
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertData(data:TransferEntity)
//
//    @Query("DELETE FROM TransferEntity where noAccount = :noAccount")
//    suspend fun deleteItemData(noAccount:String)
//
//    @Query("SELECT EXISTS (SELECT*FROM TransferEntity where noAccount = :noAccount)")
//    suspend fun isItemDataExist(noAccount:String)
//
//    @Query("SELECT * FROM TransferEntity ORDER BY id ASC")
//    fun getAllDataNoAccountSaved(): Flow<List<TransferEntity>>
//}