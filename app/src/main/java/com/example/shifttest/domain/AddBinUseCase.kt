package com.example.shifttest.domain

import com.example.shifttest.model.BinData
import javax.inject.Inject

class AddBinUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(bin: BinData): Long =
        repository.addBin(bin)
}