package com.example.shifttest.domain

import com.example.shifttest.data.remoteModel.BinData
import javax.inject.Inject

class DeleteBinUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(bin: BinData) =
        repository.deleteBin(bin)
}