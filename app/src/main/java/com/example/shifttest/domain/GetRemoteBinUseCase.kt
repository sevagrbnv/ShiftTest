package com.example.shifttest.domain

import com.example.shifttest.data.remoteModel.BinData
import com.example.shifttest.data.RepositoryImpl
import retrofit2.Response
import javax.inject.Inject

class GetRemoteBinUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(bin: String): Response<BinData>? =
        repository.getRemoteData(bin)
}