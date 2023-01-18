package com.example.shifttest.data.localDataSource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shifttest.data.remoteModel.BinData

@Dao
interface BinDao {

    @Insert(entity = BinData::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBin(bin: BinData): Long

    @Query("DELETE FROM bins WHERE id = (:binId)")
    suspend fun deleteBin(binId: Int)

    @Query("SELECT * FROM bins WHERE id = (:binId) LIMIT 1")
    suspend fun getBinById(binId: Int): BinData

    @Query("SELECT * FROM bins ORDER BY id DESC")
    fun getBinList(): LiveData<List<BinData>>
}