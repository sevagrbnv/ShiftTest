package com.example.shifttest.domain

import androidx.lifecycle.LiveData
import com.example.shifttest.model.BinData
import javax.inject.Inject

class GetBinListUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(): LiveData<List<BinData>> =
        repository.getBinList()
}