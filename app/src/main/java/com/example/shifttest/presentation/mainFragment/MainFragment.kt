package com.example.shifttest.presentation.mainFragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shifttest.R
import com.example.shifttest.databinding.FragmentMainBinding
import com.example.shifttest.presentation.mainFragment.MainRcView.BinAdapter
import com.example.shifttest.utils.ConnectionLiveData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private var openSecondFragmentListener: OpenSecondFragmentListener? = null
    private lateinit var listAdapter: BinAdapter
    private lateinit var connectionLiveData: ConnectionLiveData
    private val viewModel by viewModels<MainViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OpenSecondFragmentListener) {
            openSecondFragmentListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        openSecondFragmentListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.binList.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
            checkListForEmpty()
        }

        setConnectionLiveData() // check connection to the server
        setRecView()
        observeTilViewModel() // control for error input
        setTextChangeListener() // control of search line for empty

        binding.searchButton.setOnClickListener {
            if (connectionLiveData.value == true) {
                val bin = binding.edText.text.toString()
                if (viewModel.checkCorrect(bin))
                    openSecondFragmentListener?.openSecondFragmentByBin(bin)
            } else showErrorSnackBar()
        }
    }

    private fun setTextChangeListener() {
        binding.edText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInput()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun observeTilViewModel() {
        viewModel.errorInput.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_empty_line)
            } else {
                null
            }
            binding.til.error = message
        }
    }

    private fun setRecView() {
        listAdapter = BinAdapter()
        with(binding.rcView) {
            adapter = listAdapter
        }
        setItemClickListener()
        setSwipeListener(binding.rcView)
    }

    // for remove items
    private fun setSwipeListener(rcViewTodoList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = listAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteBin(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rcViewTodoList)
    }

    private fun setItemClickListener() {
        listAdapter.onItemClickListener = { bin ->
            bin.id?.let { itemId -> openSecondFragmentListener?.openSecondFragmentById(itemId) }
        }
    }

    // set text "history is empty" on empty list
    private fun checkListForEmpty() {
        if (viewModel.isEmptyList())
            shouldShowRcView(false)
        else shouldShowRcView(true)
    }

    //Manage visibility of RecyclerView and other elements
    private fun shouldShowRcView(show: Boolean) {
        binding.rcView.isVisible = show
        binding.emptyView.isVisible = !show
    }

    private fun setConnectionLiveData() {
        connectionLiveData = ConnectionLiveData(binding.root.context)
        connectionLiveData.observe(viewLifecycleOwner) { }
    }

    private fun showErrorSnackBar() =
        Snackbar.make(
            binding.root,
            getString(R.string.no_internet_connection),
            Snackbar.LENGTH_LONG
        ).show()

    interface OpenSecondFragmentListener {
        fun openSecondFragmentById(itemId: Int)
        fun openSecondFragmentByBin(bin: String)
    }
}