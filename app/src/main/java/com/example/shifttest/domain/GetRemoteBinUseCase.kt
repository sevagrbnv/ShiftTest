package com.example.shifttest.domain

import com.example.shifttest.model.BinData
import retrofit2.Response
import javax.inject.Inject

class GetRemoteBinUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(bin: String): Response<BinData>? =
        repository.getRemoteData(bin)
}