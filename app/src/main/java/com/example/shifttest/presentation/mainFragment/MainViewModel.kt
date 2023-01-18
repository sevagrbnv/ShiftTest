package com.example.shifttest.presentation.mainFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.shifttest.data.remoteModel.BinData
import com.example.shifttest.data.remoteDataSource.Resource
import com.example.shifttest.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRemoteBinUseCase: GetRemoteBinUseCase,
    private val getBinListUseCase: GetBinListUseCase,
    private val addBinUseCase: AddBinUseCase,
    private val deleteBinUseCase: DeleteBinUseCase
) : ViewModel() {

    val binList = getBinListUseCase.execute()

    private val _allData = MutableLiveData<Resource<BinData>>()
    val allData: LiveData<Resource<BinData>>
        get() = _allData

    fun getInfo(binString: String) = viewModelScope.launch {
        val bin = parseBin(binString)
        val validInput = isValidInput(bin)
        if (validInput) {
            _allData.postValue(Resource.Loading())
            val response = getRemoteBinUseCase.execute(bin)

            if (response?.isSuccessful == true) {
                var ready: Deferred<Unit>? = null
                response.body().let {
                    if (it != null) {
                        ready = async {
                            addBin(
                                it.copy(
                                    request = binString
                                )
                            )
                        }
                    }
                    ready?.await()
                    _allData.postValue(Resource.Success(it))
                }
            } else {
                _allData.postValue(Resource.Error(message = "404, not found"))
            }
        }
    }

    fun addBin(bin: BinData) {
        viewModelScope.launch {
            val last = addBinUseCase.execute(bin)
        }
    }

    fun deleteBin(bin: BinData) {
        viewModelScope.launch {
            deleteBinUseCase.execute(bin)
        }
    }

    fun isEmptyList(): Boolean {
        return binList.value.let {
            it?.isEmpty() ?: true
        }
    }

    private fun parseBin(binString: String?): String {
        return binString?.trim() ?: ""
    }

    private val _errorInput = MutableLiveData<Boolean>()
    val errorInput: LiveData<Boolean>
        get() = _errorInput

    private fun isValidInput(binString: String): Boolean {
        var result = true
        if (binString.isBlank()) {
            _errorInput.value = true
            result = false
        }
        return result
    }

    fun resetErrorInput() {
        _errorInput.postValue(false)
    }
}