package com.example.shifttest.presentation.mainFragment.MainRcView

import androidx.recyclerview.widget.DiffUtil
import com.example.shifttest.model.BinData

class BinDiffCallBack: DiffUtil.ItemCallback<BinData>() {

    override fun areItemsTheSame(oldItem: BinData, newItem: BinData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BinData, newItem: BinData): Boolean {
        return oldItem == newItem
    }
}