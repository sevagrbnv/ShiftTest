package com.example.shifttest.data

import androidx.lifecycle.LiveData
import com.example.shifttest.data.localDataSource.BinDao
import com.example.shifttest.data.remoteDataSource.ApiService
import com.example.shifttest.data.remoteModel.BinData
import com.example.shifttest.domain.Repository
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val binDao: BinDao
) : Repository {

    override suspend fun getRemoteData(bin: String): Response<BinData>? {
        return apiService.getResponce(bin)
    }

    override suspend fun addBin(bin: BinData): Long {
        return binDao.addBin(bin)
    }

    override suspend fun deleteBin(bin: BinData) {
        bin.id?.let { binDao.deleteBin(it) }
    }

    override suspend fun getBinById(binId: Int): BinData =
        binDao.getBinById(binId)

    override fun getBinList(): LiveData<List<BinData>> =
        binDao.getBinList()
}