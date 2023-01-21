package com.example.shifttest.presentation.detailFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shifttest.data.remoteDataSource.Resource
import com.example.shifttest.domain.AddBinUseCase
import com.example.shifttest.domain.GetBinByIdUseCase
import com.example.shifttest.domain.GetRemoteBinUseCase
import com.example.shifttest.model.BinData
import com.example.shifttest.utils.copyBin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getBinUseCase: GetBinByIdUseCase,
    private val addBinUseCase: AddBinUseCase,
    private val getRemoteBinUseCase: GetRemoteBinUseCase
) : ViewModel() {

    private val _item = MutableLiveData<BinData?>()
    val item: LiveData<BinData?>
        get() = _item

    fun getItemFromDB(itemId: Int) = viewModelScope.launch {
        val item = getBinUseCase.execute(itemId)
        _item.postValue(item)
    }

    private val _remoteBinId = MutableLiveData<Int>()
    val remoteBinId: LiveData<Int>
        get() = _remoteBinId

    private val _allData = MutableLiveData<Resource<BinData>>()
    val allData: LiveData<Resource<BinData>>
        get() = _allData

    fun getRemoteInfo(binString: String) = viewModelScope.launch {
        _allData.postValue(Resource.Loading())
        val response = getRemoteBinUseCase.execute(binString)

        if (response?.isSuccessful == true) {
            response.body().let { bin ->
                bin.let {
                    val item = copyBin(it, binString)
                    val a = launch { addBin(item) }
                    a.join()
                    _item.postValue(item)
                }
            }
        } else {
            _allData.postValue(Resource.Error(message = "404, not found"))
        }
    }

    private suspend fun addBin(bin: BinData?) {
        bin?.let { _remoteBinId.postValue(addBinUseCase.execute(it).toInt()) }
    }
}