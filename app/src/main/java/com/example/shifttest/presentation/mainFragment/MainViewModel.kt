package com.example.shifttest.presentation.mainFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shifttest.domain.AddBinUseCase
import com.example.shifttest.domain.DeleteBinUseCase
import com.example.shifttest.domain.GetBinListUseCase
import com.example.shifttest.domain.GetRemoteBinUseCase
import com.example.shifttest.model.BinData
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun checkCorrect(binString: String): Boolean {
        val bin = parseBin(binString)
        return isValidInput(bin)
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