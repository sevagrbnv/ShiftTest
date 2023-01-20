package com.example.shifttest.domain

import com.example.shifttest.model.BinData
import javax.inject.Inject

class GetBinByIdUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(binId: Int): BinData =
        repository.getBinById(binId)
}