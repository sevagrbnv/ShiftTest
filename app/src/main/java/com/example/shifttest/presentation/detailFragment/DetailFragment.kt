package com.example.shifttest.presentation.detailFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shifttest.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    val viewModel by viewModels<DetailViewModel>()
    private var itemId = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
    }

    private fun initValues() {
        viewModel.getItem(itemId)
        viewModel.item.observe(viewLifecycleOwner) {

            binding.request.text = it.request
            binding.number.text = "length: ${it.number.length}, luhn: ${it.number.luhn}"
            binding.scheme.text = "scheme: ${it.scheme}"
            binding.type.text = "type: ${it.type}"
            binding.brand.text = "brand: ${it.brand}"
            binding.prepaid.text = "prepaid: ${it.prepaid.toString()}"

            binding.cnumeric.text = "numeric: ${it.country.numeric}"
            binding.calpha.text = "alpha2: ${it.country.alpha2}"
            binding.cname.text = "name: ${it.country.name}"
            binding.cemoji.text = "emoji: ${it.country.emoji}"
            binding.ccurrency.text = "curency: ${it.country.currency}"
            binding.clatitude.text = "lat: ${it.country.latitude}"
            binding.clongitude.text = "lon: ${it.country.longitude}"

            binding.bname.text = "name: ${it.bank.name}"
            binding.burl.text = "url: ${it.bank.url}"
            binding.bphone.text = "phone: ${it.bank.phone}"
            binding.bcity.text = "city: ${it.bank.city}"

            binding.bphone.setOnClickListener { view ->
                if (it.bank.phone != "-") {
                    val intent =
                        Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", it.bank.phone, null))
                    startActivity(intent)
                }
            }

            binding.burl.setOnClickListener { view ->
                val i = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://${it.bank.url}")
                )
                startActivity(i)
            }

            binding.columnOne.setOnClickListener { view ->
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q=${it.country.latitude},${it.country.longitude}")
                )
                startActivity(intent)
            }
        }
    }

    private fun parseParam() {
        val args = requireArguments()
        if (!args.containsKey(ID_KEY))
            throw RuntimeException("Id not found")
        itemId = args.getInt(ID_KEY)
    }

    companion object {

        private const val UNDEFINED_ID = -1
        private const val ID_KEY = "ID"

        fun newInstance(binId: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID_KEY, binId)
                }
            }
    }
}