package com.example.shifttest.presentation.detailFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shifttest.data.remoteModel.BinData
import com.example.shifttest.domain.GetBinByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getBinUseCase: GetBinByIdUseCase
) : ViewModel() {

    private val _item = MutableLiveData<BinData>()
    val item: LiveData<BinData>
        get() = _item

    fun getItem(itemId: Int) =
        viewModelScope.async {
            val item = getBinUseCase.execute(itemId)
            _item.postValue(item)
        }

}