package com.example.shifttest.presentation.mainFragment.MainRcView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shifttest.R
import com.example.shifttest.data.remoteModel.BinData
import com.example.shifttest.databinding.BinItemBinding

class BinAdapter: ListAdapter<BinData, BinViewHolder>(
    BinDiffCallBack()
) {

    var onItemClickListener: ((BinData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinViewHolder {
        val layout = R.layout.bin_item
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return BinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BinViewHolder, position: Int) {
        val binItem = getItem(position)
        val binding = holder.binding

        when (binding) {
            is BinItemBinding  -> {
                binding.bin = binItem
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(binItem)
                }
            }
        }
    }

    companion object {
        const val ITEM_IS_COMPLETE = 0
        const val MAX_POOL_SIZE = 30
    }
}