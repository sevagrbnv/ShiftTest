package com.example.shifttest.presentation.detailFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shifttest.R
import com.example.shifttest.data.remoteDataSource.Resource
import com.example.shifttest.databinding.FragmentDetailBinding
import com.example.shifttest.model.BinData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    val viewModel by viewModels<DetailViewModel>()
    private var itemId = UNDEFINED_ID
    private var itemBin = UNDEFINED_BIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNetworkLiveData()
        initData()
    }

    private fun initData() {
        if (itemId != UNDEFINED_ID)
            viewModel.getItemFromDB(itemId)
        else if (itemBin != UNDEFINED_BIN)
            viewModel.getRemoteInfo(itemBin)
        else throw RuntimeException("UNDEFINED MODE")
        viewModel.item.observe(viewLifecycleOwner) { bin ->
            bin?.let {
                setData(it)
            }
        }
    }

    private fun setNetworkLiveData() {
        viewModel.allData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    shouldToShowProgressBar(false)
                }
                is Resource.Error -> {
                    shouldToShowProgressBar(false)
                    binding.request.text = getString(R.string.not_found)
                    Snackbar.make(binding.root, getString(R.string.not_found), Snackbar.LENGTH_LONG)
                        .show()
                }
                is Resource.Loading -> {
                    shouldToShowProgressBar(true)
                }
            }
        }
    }

    private fun shouldToShowProgressBar(show: Boolean) {
        binding.progressBar.isVisible = show
        binding.columns.isVisible = !show
    }

    private fun setClickListeners() {
        val item = viewModel.item.value

        binding.bphone.setOnClickListener {
            val phone = item?.bank?.phone
            if (phone != "-") {
                val intent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.fromParts("tel", phone, null)
                )
                startActivity(intent)
            }
        }

        binding.burl.setOnClickListener {
            val url = item?.bank?.url
            if (url != "-") {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://${url}")
                )
                startActivity(intent)
            }
        }

        binding.columnOne.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "geo:0,0?q=${(item?.country?.latitude)}," +
                            "${(item?.country?.longitude)}"
                )
            )
            startActivity(intent)
        }
    }

    private fun setData(item: BinData) {
        shouldToShowProgressBar(false)
        binding.request.text = item.request
        binding.number.text = "length: ${item.number.length}, luhn: ${item.number.luhn}"
        binding.scheme.text = "scheme: ${item.scheme}"
        binding.type.text = "type: ${item.type}"
        binding.brand.text = "brand: ${item.brand}"
        binding.prepaid.text = "prepaid: ${item.prepaid.toString()}"

        binding.cnumeric.text = "numeric: ${item.country.numeric}"
        binding.calpha.text = "alpha2: ${item.country.alpha2}"
        binding.cname.text = "name: ${item.country.name}"
        binding.cemoji.text = "emoji: ${item.country.emoji}"
        binding.ccurrency.text = "curency: ${item.country.currency}"
        binding.clatitude.text = "lat: ${item.country.latitude}"
        binding.clongitude.text = "lon: ${item.country.longitude}"

        binding.bname.text = "name: ${item.bank.name}"
        binding.burl.text = "url: ${item.bank.url}"
        binding.bphone.text = "phone: ${item.bank.phone}"
        binding.bcity.text = "city: ${item.bank.city}"

        setClickListeners()
    }

    private fun parseParam() {
        val args = requireArguments()
        if (args.containsKey(ID_KEY))
            itemId = args.getInt(ID_KEY)
        else if (args.containsKey(BIN))
            itemBin = args.getString(BIN).toString()
        else throw RuntimeException("Parameters not found")
    }

    companion object {

        private const val UNDEFINED_BIN = ""
        private const val UNDEFINED_ID = -1
        private const val ID_KEY = "ID"
        private const val BIN = "BIN"

        fun newInstanceFromDB(binId: Int) = DetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ID_KEY, binId)
            }
        }

        fun newInstanceFromRemote(bin: String) = DetailFragment().apply {
            arguments = Bundle().apply {
                putString(BIN, bin)
            }
        }
    }
}