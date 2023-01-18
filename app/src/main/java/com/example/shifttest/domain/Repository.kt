package com.example.shifttest.domain

import androidx.lifecycle.LiveData
import com.example.shifttest.data.remoteModel.BinData
import retrofit2.Response

interface Repository {

    suspend fun getRemoteData(bin: String): Response<BinData>?

    suspend fun addBin(bin: BinData): Long

    suspend fun deleteBin(bin: BinData)

    suspend fun getBinById(binId: Int): BinData

    fun getBinList(): LiveData<List<BinData>>
}